package com.nexus.aura.backend.nexus_aura_backend.service

import com.nexus.aura.backend.nexus_aura_backend.dto.CreatePostRequest
import com.nexus.aura.backend.nexus_aura_backend.dto.UserProfileUpdateRequest
import com.nexus.aura.backend.nexus_aura_backend.dto.UserRegistrationRequest
import com.nexus.aura.backend.nexus_aura_backend.dto.UserResponse
import com.nexus.aura.backend.nexus_aura_backend.entity.Comment
import com.nexus.aura.backend.nexus_aura_backend.entity.Post
import com.nexus.aura.backend.nexus_aura_backend.entity.User
import com.nexus.aura.backend.nexus_aura_backend.event.UserRegisteredEvent
import com.nexus.aura.backend.nexus_aura_backend.exception.UserAlreadyExistsException
import com.nexus.aura.backend.nexus_aura_backend.exception.UserNotFoundException
import com.nexus.aura.backend.nexus_aura_backend.exception.UsernameAlreadyTakenException
import com.nexus.aura.backend.nexus_aura_backend.repository.CommentRepository
import com.nexus.aura.backend.nexus_aura_backend.repository.PostRepository
import com.nexus.aura.backend.nexus_aura_backend.repository.UserRepository
import com.nexus.aura.backend.nexus_aura_backend.service.strategy.EmailLookupStrategy
import com.nexus.aura.backend.nexus_aura_backend.service.strategy.UserLookupContext
import com.nexus.aura.backend.nexus_aura_backend.service.strategy.UsernameLookupStrategy
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface IUserService {
    fun registerUser(request: UserRegistrationRequest): UserResponse
    fun updateUserProfile(email: String, request: UserProfileUpdateRequest): UserResponse
    fun findUserByIdentifier(identifier: String): User?
    fun getUserByUsername(username: String): UserResponse
    fun getUserPosts(username: String): List<Post>
    fun createPost(username: String, request: CreatePostRequest): Any
    fun getUserFeed(username: String): List<Post>
    fun addCommentToPost(username: String, postId: UUID, content: String): Comment
    fun likePost(postId: UUID): Post
}

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val eventPublisher: ApplicationEventPublisher,
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository
) : IUserService {
    private val userLookupContext = UserLookupContext(
        listOf(
            EmailLookupStrategy(userRepository),
            UsernameLookupStrategy(userRepository)
        )
    )

    override fun registerUser(request: UserRegistrationRequest): UserResponse {
        if (userRepository.existsByEmail(request.email)) {
            throw UserAlreadyExistsException("Email is already in use")
        }
        if (userRepository.existsByUsername(request.username)) {
            throw UsernameAlreadyTakenException("Username is already taken")
        }

        val user = User.Builder()
            .username(request.username)
            .email(request.email)
            .passwordHash(passwordEncoder.encode(request.password))
            .fullName(request.fullName)
            .bio(request.bio)
            .profilePictureUrl(request.profilePictureUrl)
            .isLocked(false)
            .isDisabled(false)
            .isEmailVerified(false)
            .build()

        val savedUser = userRepository.save(user)
        eventPublisher.publishEvent(UserRegisteredEvent(savedUser))
        return UserResponse(
            id = savedUser.id,
            username = savedUser.username,
            email = savedUser.email,
            fullName = savedUser.fullName,
            bio = savedUser.bio,
            profilePictureUrl = savedUser.profilePictureUrl,
            createdAt = savedUser.createdAt
        )
    }

    override fun updateUserProfile(username: String, request: UserProfileUpdateRequest): UserResponse {
        val user = userRepository.findByUsername(username)
            ?: throw UserNotFoundException("User not found")

        request.fullName?.let { user.fullName = it }
        request.bio?.let { user.bio = it }
        request.profilePictureUrl?.let { user.profilePictureUrl = it }

        val saved = userRepository.save(user)

        return UserResponse(
            id = saved.id,
            username = saved.username,
            email = saved.email,
            fullName = saved.fullName,
            bio = saved.bio,
            profilePictureUrl = saved.profilePictureUrl,
            createdAt = saved.createdAt
        )
    }

    override fun findUserByIdentifier(identifier: String): User? {
        return userLookupContext.findUser(identifier)
    }

    override fun getUserByUsername(username: String): UserResponse {
        val user = userRepository.findByUsername(username)
            ?: throw UserNotFoundException("User not found")
        return UserResponse(
            id = user.id,
            username = user.username,
            email = user.email,
            fullName = user.fullName,
            bio = user.bio,
            profilePictureUrl = user.profilePictureUrl,
            createdAt = user.createdAt
        )
    }

    override fun getUserPosts(username: String): List<Post> {
        val user = userRepository.findByUsername(username)
            ?: throw UserNotFoundException("User not found")
        return user.posts

    }

    @Transactional
    override fun createPost(username: String, request: CreatePostRequest): Post {
        val user = userRepository.findByUsername(username)
            ?: throw UserNotFoundException("User not found")
        val post = Post(
            content = request.content,
            user = user
        )
        return postRepository.save(post)
    }

    override fun getUserFeed(username: String): List<Post> {
        val user = userRepository.findByUsername(username)
            ?: throw UserNotFoundException("User not found")
        val followingIds = user.following.map { it.id }
        if (followingIds.isEmpty()) return emptyList()
        val posts = postRepository.findAllByUserIdInOrderByCreatedAtDesc(followingIds)
        // Sort by createdAt (desc), then by likes (desc), then shuffle posts with same createdAt and likes
        return posts.groupBy { Pair(it.createdAt, it.likes) }
            .toSortedMap(compareByDescending<Pair<java.util.Date, Int>> { it.first }.thenByDescending { it.second })
            .values
            .flatMap { it.shuffled() }
    }

    @Transactional
    override fun addCommentToPost(username: String, postId: UUID, content: String): Comment {
        val user = userRepository.findByUsername(username)
            ?: throw UserNotFoundException("User not found")
        val post = postRepository.findById(postId).orElseThrow { UserNotFoundException("Post not found") }
        val comment = Comment(content = content, user = user, post = post)
        return commentRepository.save(comment)
    }

    @Transactional
    override fun likePost(postId: UUID): Post {
        val post = postRepository.findById(postId).orElseThrow { UserNotFoundException("Post not found") }
        post.likes += 1
        return postRepository.save(post)
    }
}
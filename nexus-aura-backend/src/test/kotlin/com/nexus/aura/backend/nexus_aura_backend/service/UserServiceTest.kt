package com.nexus.aura.backend.nexus_aura_backend.service

import com.nexus.aura.backend.nexus_aura_backend.dto.CreatePostRequest
import com.nexus.aura.backend.nexus_aura_backend.dto.UserRegistrationRequest
import com.nexus.aura.backend.nexus_aura_backend.entity.User
import com.nexus.aura.backend.nexus_aura_backend.repository.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.util.*

@SpringBootTest
class UserServiceTest {
    @Autowired
    private lateinit var userService: UserService

    @MockBean
    private lateinit var userRepository: UserRepository
    @MockBean
    private lateinit var postRepository: PostRepository
    @MockBean
    private lateinit var commentRepository: CommentRepository

    @Test
    fun `register user should create and return user response`() {
        val request = UserRegistrationRequest(
            username = "testuser",
            email = "test@example.com",
            password = "password123",
            fullName = "Test User",
            bio = "Hello!",
            profilePictureUrl = null
        )
        Mockito.`when`(userRepository.existsByEmail(request.email)).thenReturn(false)
        Mockito.`when`(userRepository.existsByUsername(request.username)).thenReturn(false)
        val response = userService.registerUser(request)
        assertEquals(request.username, response.username)
        assertEquals(request.email, response.email)
    }

    @Test
    fun `create post should add post to user`() {
        // Arrange
        val username = "testuser"
        val request = CreatePostRequest(content = "Hello World!")
        val user = User.Builder().username(username).email("test@example.com").passwordHash("hash").build()
        Mockito.`when`(userRepository.findByUsername(username)).thenReturn(user)
        // Act
        val post = userService.createPost(username, request)
        // Assert
        assertEquals(request.content, post.content)
        assertEquals(user, post.user)
    }

    @Test
    fun `like post should increment likes`() {
        val postId = UUID.randomUUID()
        val user = User.Builder().username("testuser").email("test@example.com").passwordHash("hash").build()
        val post = Post(id = postId, content = "Test post", user = user)
        Mockito.`when`(postRepository.findById(postId)).thenReturn(Optional.of(post))
        Mockito.`when`(postRepository.save(any(Post::class.java))).thenAnswer { it.arguments[0] }
        val likedPost = userService.likePost(postId)
        assertEquals(1, likedPost.likes)
    }

    @Test
    fun `add comment to post should save comment`() {
        val postId = UUID.randomUUID()
        val username = "testuser"
        val user = User.Builder().username(username).email("test@example.com").passwordHash("hash").build()
        val post = Post(id = postId, content = "Test post", user = user)
        val commentContent = "Nice post!"
        Mockito.`when`(userRepository.findByUsername(username)).thenReturn(user)
        Mockito.`when`(postRepository.findById(postId)).thenReturn(Optional.of(post))
        Mockito.`when`(commentRepository.save(any(Comment::class.java))).thenAnswer { it.arguments[0] }
        val comment = userService.addCommentToPost(username, postId, commentContent)
        assertEquals(commentContent, comment.content)
        assertEquals(user, comment.user)
        assertEquals(post, comment.post)
    }

    @Test
    fun `get user feed should return posts from followed users sorted by recency and likes`() {
        val username = "testuser"
        val followedUser1 = User.Builder().username("followed1").email("f1@example.com").passwordHash("hash").build()
        val followedUser2 = User.Builder().username("followed2").email("f2@example.com").passwordHash("hash").build()
        val user = User.Builder().username(username).email("test@example.com").passwordHash("hash").build().apply {
            following.addAll(listOf(followedUser1, followedUser2))
        }
        val post1 = Post(id = UUID.randomUUID(), content = "Post 1", user = followedUser1, likes = 2)
        val post2 = Post(id = UUID.randomUUID(), content = "Post 2", user = followedUser2, likes = 5)
        val post3 = Post(id = UUID.randomUUID(), content = "Post 3", user = followedUser1, likes = 1)
        val posts = listOf(post1, post2, post3)
        Mockito.`when`(userRepository.findByUsername(username)).thenReturn(user)
        Mockito.`when`(postRepository.findAllByUserIdInOrderByCreatedAtDesc(listOf(followedUser1.id, followedUser2.id))).thenReturn(posts)
        val feed = userService.getUserFeed(username)
        assertEquals(listOf(post2, post1, post3), feed.sortedWith(compareByDescending<Post> { it.createdAt }.thenByDescending { it.likes }))
    }

    // More tests for feed, comments, and likes can be added similarly
}

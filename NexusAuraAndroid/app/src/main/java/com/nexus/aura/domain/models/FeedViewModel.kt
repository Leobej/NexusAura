package com.nexus.aura.domain.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexus.aura.data.model.Post
import com.nexus.aura.domain.repository.FeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val repository: FeedRepository
) : ViewModel() {

    private val _feedPosts = MutableStateFlow<List<Post>>(emptyList())
    val feedPosts: StateFlow<List<Post>> = _feedPosts.asStateFlow()

    init {
        loadPosts()
    }

    private fun loadPosts() {
        viewModelScope.launch {
            _feedPosts.value = repository.getFeedPosts()
        }
    }

    fun toggleLike(post: Post) {
        // Simulate toggling like (you can update your backend here)
        _feedPosts.value = _feedPosts.value.map {
            if (it.id == post.id) it.copy(isLiked = !it.isLiked) else it
        }
    }
}
package com.example.profileapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profileapp.data.remote.HttpClientFactory
import com.example.profileapp.data.remote.PostApiService
import com.example.profileapp.data.repository.PostRepository
import com.example.profileapp.data.PostListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {

    private val repository = PostRepository(
        PostApiService(HttpClientFactory.create())
    )


    private val _listUiState = MutableStateFlow<PostListUiState>(PostListUiState.Loading)
    val listUiState: StateFlow<PostListUiState> = _listUiState

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    private val _detailUiState = MutableStateFlow<PostListUiState>(PostListUiState.Loading)
    val detailUiState: StateFlow<PostListUiState> = _detailUiState

    private val _createState = MutableStateFlow<PostListUiState?>(null)
    val createState: StateFlow<PostListUiState?> = _createState

    init {
        loadPosts()
    }

    fun loadPosts() {
        viewModelScope.launch {
            _listUiState.value = PostListUiState.Loading

            repository.getPosts()
                .onSuccess {
                    _listUiState.value = PostListUiState.Success(it)
                }
                .onFailure {
                    _listUiState.value = PostListUiState.Error(
                        it.message ?: "Terjadi kesalahan"
                    )
                }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true

            repository.getPosts()
                .onSuccess {
                    _listUiState.value = PostListUiState.Success(it)
                }
                .onFailure {
                    _listUiState.value = PostListUiState.Error(
                        it.message ?: "Gagal refresh"
                    )
                }

            _isRefreshing.value = false
        }
    }

    fun loadPostDetail(id: Int) {
        viewModelScope.launch {
            _detailUiState.value = PostListUiState.Loading

            repository.getPosts()
                .onSuccess { list ->
                    val post = list.find { it.id == id }

                    if (post != null) {
                        _detailUiState.value = PostListUiState.Success(listOf(post))
                    } else {
                        _detailUiState.value = PostListUiState.Error("Data tidak ditemukan")
                    }
                }
                .onFailure {
                    _detailUiState.value = PostListUiState.Error(
                        it.message ?: "Gagal ambil detail"
                    )
                }
        }
    }

    fun createPost(title: String, body: String) {
        viewModelScope.launch {
            _createState.value = PostListUiState.Loading

            repository.createPost(title, body)
                .onSuccess {
                    _createState.value = PostListUiState.Success(listOf(it))
                    loadPosts()
                }
                .onFailure {
                    _createState.value = PostListUiState.Error(
                        it.message ?: "Gagal tambah data"
                    )
                }
        }
    }
}
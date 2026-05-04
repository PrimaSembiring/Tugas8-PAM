package com.example.profileapp.data

import com.example.profileapp.data.remote.PostDto

/**
 * Sealed class untuk merepresentasikan semua kemungkinan UI state saat networking.
 *
 * - Loading  : request sedang berjalan, tampilkan spinner
 * - Success  : data berhasil didapat, tampilkan list/detail
 * - Error    : request gagal, tampilkan pesan error + tombol retry
 *
 * Menggunakan sealed class (bukan enum) karena tiap state bisa punya data berbeda.
 */
sealed class PostListUiState {
    object Loading : PostListUiState()
    data class Success(val posts: List<PostDto>) : PostListUiState()
    data class Error(val message: String) : PostListUiState()
}

sealed class PostDetailUiState {
    object Loading : PostDetailUiState()
    data class Success(val post: PostDto) : PostDetailUiState()
    data class Error(val message: String) : PostDetailUiState()
}

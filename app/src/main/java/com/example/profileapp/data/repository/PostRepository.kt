package com.example.profileapp.data.repository

import com.example.profileapp.data.remote.PostApiService
import com.example.profileapp.data.remote.PostDto

class PostRepository(private val api: PostApiService) {

    suspend fun getPosts(): Result<List<PostDto>> {
        return try {
            Result.success(api.getPosts())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createPost(title: String, body: String): Result<PostDto> {
        return try {
            Result.success(api.createPost(title, body))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
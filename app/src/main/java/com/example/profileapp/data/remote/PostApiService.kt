package com.example.profileapp.data.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class PostApiService(private val client: HttpClient) {

    private val baseUrl = "https://jsonplaceholder.typicode.com"

    suspend fun getPosts(): List<PostDto> {
        return client.get("$baseUrl/posts").body()
    }

    suspend fun createPost(title: String, body: String): PostDto {
        return client.post("$baseUrl/posts") {
            contentType(ContentType.Application.Json)
            setBody(
                mapOf(
                    "title" to title,
                    "body" to body,
                    "userId" to 1
                )
            )
        }.body()
    }
}
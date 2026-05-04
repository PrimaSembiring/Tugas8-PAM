package com.example.profileapp.data.repository

import com.example.profileapp.data.local.NoteLocalDataSource
import com.example.profileapp.data.remote.PostApiService
import com.example.profileapp.NoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

class NoteRepository(
    private val localDataSource: NoteLocalDataSource,
    private val remoteDataSource: PostApiService? = null
) {

    fun getAllNotes(): Flow<List<NoteEntity>> {
        return localDataSource.getAllNotes()
            .onStart {
                syncFromRemoteIfAvailable()
            }
    }

    fun getFavoriteNotes(): Flow<List<NoteEntity>> {
        return localDataSource.getFavoriteNotes()
    }

    fun searchNotes(query: String): Flow<List<NoteEntity>> {
        return if (query.isBlank()) getAllNotes()
        else localDataSource.searchNotes(query)
    }

    suspend fun getNoteById(id: Long): NoteEntity? {
        return localDataSource.getNoteById(id)
    }

    suspend fun addNote(title: String, content: String) {
        localDataSource.insertNote(title, content)

        try {
            remoteDataSource?.createPost(title, content)
        } catch (_: Exception) {
        }
    }

    suspend fun updateNote(id: Long, title: String, content: String) {
        localDataSource.updateNote(id, title, content)
    }

    suspend fun toggleFavorite(id: Long, isFavorite: Boolean) {
        localDataSource.toggleFavorite(id, isFavorite)
    }

    suspend fun deleteNote(id: Long) {
        localDataSource.deleteNote(id)
    }

    private suspend fun syncFromRemoteIfAvailable() {
        try {
            remoteDataSource?.let { api ->
                val remotePosts = api.getPosts()
                val notes = remotePosts.take(10).map { post ->
                    Triple(post.title, post.body, System.currentTimeMillis())
                }
                localDataSource.insertAll(notes)
            }
        } catch (_: Exception) {
        }
    }
}
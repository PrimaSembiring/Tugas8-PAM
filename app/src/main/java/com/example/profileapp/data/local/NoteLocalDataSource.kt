package com.example.profileapp.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.profileapp.NotesDatabase
import com.example.profileapp.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class NoteLocalDataSource(database: NotesDatabase) {

    private val queries = database.noteQueries

    fun getAllNotes(): Flow<List<NoteEntity>> {
        return queries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    fun getFavoriteNotes(): Flow<List<NoteEntity>> {
        return queries.selectFavorites()
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    fun searchNotes(query: String): Flow<List<NoteEntity>> {
        val pattern = "%$query%"
        return queries.search(pattern, pattern)
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    suspend fun getNoteById(id: Long): NoteEntity? {
        return withContext(Dispatchers.IO) {
            queries.selectById(id).executeAsOneOrNull()
        }
    }

    suspend fun insertNote(title: String, content: String) {
        val now = System.currentTimeMillis()
        withContext(Dispatchers.IO) {
            queries.insert(
                title = title,
                content = content,
                is_favorite = 0L,
                created_at = now,
                updated_at = now
            )
        }
    }

    suspend fun updateNote(id: Long, title: String, content: String) {
        val now = System.currentTimeMillis()
        withContext(Dispatchers.IO) {
            queries.update(
                title = title,
                content = content,
                updated_at = now,
                id = id
            )
        }
    }

    suspend fun toggleFavorite(id: Long, isFavorite: Boolean) {
        val now = System.currentTimeMillis()
        withContext(Dispatchers.IO) {
            queries.toggleFavorite(
                is_favorite = if (isFavorite) 1L else 0L,
                updated_at = now,
                id = id
            )
        }
    }

    suspend fun deleteNote(id: Long) {
        withContext(Dispatchers.IO) {
            queries.delete(id)
        }
    }

    suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            queries.deleteAll()
        }
    }

    suspend fun insertAll(notes: List<Triple<String, String, Long>>) {
        withContext(Dispatchers.IO) {
            queries.transaction {
                notes.forEach { (title, content, createdAt) ->
                    queries.insert(
                        title = title,
                        content = content,
                        is_favorite = 0L,
                        created_at = createdAt,
                        updated_at = createdAt
                    )
                }
            }
        }
    }
}
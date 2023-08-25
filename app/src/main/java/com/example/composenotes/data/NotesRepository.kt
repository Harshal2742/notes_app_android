package com.example.composenotes.data


import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    suspend fun addNote(note: Note)

    suspend fun deleteNote(note: Note)
    suspend fun updateNote(note: Note)

    fun getNote(id: Int): Flow<Note>

    fun getAllNotes(): Flow<List<Note>>
}
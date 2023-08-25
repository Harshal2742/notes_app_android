package com.example.composenotes.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class OfflineNotesRepository @Inject constructor(private val notesDao: NotesDao) : NotesRepository {
    override suspend fun addNote(note: Note) = notesDao.addNote(note)

    override suspend fun deleteNote(note: Note) = notesDao.deleteNote(note)

    override suspend fun updateNote(note: Note) = notesDao.updateNote(note)

    override fun getNote(id: Int): Flow<Note> = notesDao.getNote(id)

    override fun getAllNotes(): Flow<List<Note>> = notesDao.getAllNotes()
}
package com.example.composenotes.ui.screens

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composenotes.data.Note
import com.example.composenotes.data.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "NoteScreenViewModel"

@HiltViewModel
class NoteScreenViewModel @Inject constructor(
    private val notesRepository: NotesRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val note = MutableStateFlow(Note(-1, "", "", ""))

    init {
        getNote()
    }

    private fun getNote() {
        val id = savedStateHandle.get<Int>("noteId")
        viewModelScope.launch {
            try {
                note.value = notesRepository.getNote(id!!).first()
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
            }
        }
    }

    fun deleteNote() {
        viewModelScope.launch {
            try {
                notesRepository.deleteNote(note = note.value)
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
            }
        }
    }

    fun updateTitle(title: String) {
        note.update { Note -> Note.copy(title = title) }
    }

    fun updateDescription(description: String) {
        note.update { Note -> Note.copy(description = description) }
    }

    fun updateNote() {
        viewModelScope.launch {
            notesRepository.updateNote(note.value)
        }
    }

}
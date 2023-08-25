package com.example.composenotes.ui.screens

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composenotes.data.Note
import com.example.composenotes.data.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Date
import javax.inject.Inject

private const val TAG = "CreateNoteViewModel"

@HiltViewModel
class CreateNoteViewModel @Inject constructor(private val notesRepository: NotesRepository) :
    ViewModel() {
    val note = MutableStateFlow(Note(title = "", description = "", date = getCurrentDate()))

    fun setTitle(title: String) {
        note.update { Note -> Note.copy(title = title) }
    }

    fun setDescription(description: String) {
        note.update { Note -> Note.copy(description = description) }
    }

    fun saveNote() {
        viewModelScope.launch {
            try {
                notesRepository.addNote(note.value)
            } catch (e: IOException) {
                Log.d(TAG, e.message.toString())
            }
        }
    }
}

fun getCurrentDate(): String {
    val sdf = SimpleDateFormat.getDateInstance()
    return sdf.format(Date())
}
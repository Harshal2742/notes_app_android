package com.example.composenotes.ui.screens


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composenotes.ui.NotesAppScreen
import com.example.composenotes.ui.ScaffoldState

@Composable
fun CreateNoteScreen(
    onComposing: (ScaffoldState) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CreateNoteViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        onComposing(
            ScaffoldState(
                topAppBarTitle = context.getString(NotesAppScreen.CreateNote.title),
                topAppBarAction = {
                    IconButton(onClick = {
                        viewModel.saveNote()
                        navigateBack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Create new note"
                        )
                    }
                }
            )
        )
    }


    val note = viewModel.note.collectAsState()

    Box(modifier = modifier.fillMaxSize()) {
        NoteDisplayLayout(
            date = note.value.date,
            title = note.value.title,
            description = note.value.description,
            onTitleChange = { viewModel.setTitle(it) },
            onDescriptionChange = { viewModel.setDescription(it) }
        )
    }
}
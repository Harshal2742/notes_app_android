package com.example.composenotes.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composenotes.ui.ScaffoldState

@Composable
fun NoteScreen(
    onComposing: (ScaffoldState) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NoteScreenViewModel = hiltViewModel()
) {

    val note = viewModel.note.collectAsState()


    LaunchedEffect(key1 = Unit) {
        onComposing(
            ScaffoldState(
                topAppBarAction = {
                    IconButton(onClick = {
                        viewModel.deleteNote()
                        navigateBack()
                    }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete note")
                    }
                    IconButton(onClick = {
                        viewModel.updateNote()
                        navigateBack()
                    }) {
                        Icon(imageVector = Icons.Filled.Done, contentDescription = "Save changes")
                    }
                })
        )
    }


    Box(modifier = modifier.fillMaxSize()) {
        NoteDisplayLayout(
            date = note.value.date,
            title = note.value.title,
            description = note.value.description,
            onTitleChange = { viewModel.updateTitle(it) },
            onDescriptionChange = { viewModel.updateDescription(it) },
            modifier = modifier
        )
    }
}


@Preview(showBackground = true, heightDp = 800)
@Composable
fun NoteScreenPreview() {
//    NoteScreen()
}


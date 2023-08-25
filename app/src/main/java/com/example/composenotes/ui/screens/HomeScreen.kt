package com.example.composenotes.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composenotes.data.Note
import com.example.composenotes.ui.NotesAppScreen
import com.example.composenotes.ui.ScaffoldState

@Composable
fun HomeScreen(
    onComposing: (ScaffoldState) -> Unit,
    onNoteClick: (note: Note) -> Unit,
    modifier: Modifier = Modifier,
    onFabClick: () -> Unit,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        onComposing(
            ScaffoldState(
                topAppBarTitle = context.getString(NotesAppScreen.Start.title),
                fab = {
                    FloatingActionButton(onClick = onFabClick) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "Create new note")
                    }
                },
                showBottomNavigation = true
            )
        )
    }

    val notes = viewModel.notes.collectAsState()


    if (notes.value.isEmpty()) {
        NoNotesCreatedMessage()
    } else {
        AllNotes(
            notes = notes.value,
            onNoteClick = onNoteClick,
            modifier = modifier.padding(vertical = 16.dp)
        )
    }

}

@Composable
fun NoNotesCreatedMessage(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "No notes created yet!", fontSize = 24.sp, color = Color.LightGray)
    }
}

@Composable
fun AllNotes(notes: List<Note>, onNoteClick: (note: Note) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier, content = {
        items(notes) {
            NoteCard(
                note = it, onNoteClick = onNoteClick, modifier = Modifier.padding(
                    horizontal = 16.dp, vertical = 4.dp
                )
            )
        }
    })
}

@Composable
fun NoteCard(note: Note, onNoteClick: (note: Note) -> Unit, modifier: Modifier = Modifier) {
    Card(modifier = modifier
        .clickable {
            onNoteClick(note)
        }
        .fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = note.date,
                fontSize = 10.sp,
                modifier = Modifier.padding(bottom = 8.dp),
                color = Color.Gray
            )

            if (!note.title.isNullOrEmpty()) {
                Text(
                    text = note.title,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    modifier = Modifier.padding(bottom = 4.dp),
                )
            }
            Text(
                text = note.description, maxLines = 1
            )
        }

    }
}

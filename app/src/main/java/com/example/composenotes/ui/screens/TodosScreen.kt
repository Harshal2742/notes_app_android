package com.example.composenotes.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composenotes.data.DataSource
import com.example.composenotes.data.Todo
import com.example.composenotes.ui.NotesAppScreen
import com.example.composenotes.ui.ScaffoldState


@Composable
fun TodosScreen(onComposing: (ScaffoldState) -> Unit) {

    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        onComposing(
            (ScaffoldState(
                topAppBarTitle = context.getString(NotesAppScreen.Todos.title),
                showBottomNavigation = true
            ))
        )
    }

    Column() {

        TodosList(
            todos = DataSource.Todos.sortedBy { it.isDone },
            modifier = Modifier.fillMaxHeight(1f)
        )
    }
}

@Composable
fun TodosList(todos: List<Todo>, modifier: Modifier = Modifier) {

    var isFirstDoneTodo = true;
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        items(items = todos, key = { it.id }) {

            if (it.isDone && isFirstDoneTodo) {
                Text(
                    text = "Done",
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 24.dp,
                        bottom = 8.dp
                    )
                )
                isFirstDoneTodo = false;
            }
            TodosListItem(todo = it, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun TodosListItem(todo: Todo, modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            Checkbox(
                checked = todo.isDone,
                onCheckedChange = {},
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(text = todo.todo)
        }
    }
}

@Preview(showBackground = true, widthDp = 450)
@Composable
fun TodosListPreview() {
    TodosList(todos = DataSource.Todos)
}
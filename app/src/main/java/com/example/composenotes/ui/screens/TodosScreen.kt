package com.example.composenotes.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.RemoveCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composenotes.data.DataSource
import com.example.composenotes.data.Todo
import com.example.composenotes.ui.NotesAppScreen
import com.example.composenotes.ui.ScaffoldState


@Composable
fun TodosScreen(
    onComposing: (ScaffoldState) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TodoViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    var showEditTodoBottomSheet by rememberSaveable { mutableStateOf(false) }
    var showAddTodoBottomSheet by rememberSaveable { mutableStateOf(false) }

    val allTodos = viewModel.allTodos.collectAsState()
    val selectedTodo = viewModel.selectedTodo.collectAsState()
    val newTodo = viewModel.newTodo.collectAsState()

    LaunchedEffect(key1 = Unit) {
        onComposing(
            (ScaffoldState(topAppBarTitle = context.getString(NotesAppScreen.Todos.title),
                showBottomNavigation = true,
                fab = {
                    FloatingActionButton(onClick = {
                        showAddTodoBottomSheet = true
                    }) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "Create new todo")
                    }
                }))
        )
    }

    Column(modifier = modifier.fillMaxWidth()) {
        TodosList(todos = allTodos.value, onTodoClick = {
            viewModel.onTodoSelected(it)
            showEditTodoBottomSheet = true
        }, onIsDoneChange = { id, isDone ->
            viewModel.updateTodoDone(id, isDone)
        }, onRemove = viewModel::deleteTodo, modifier = Modifier.fillMaxWidth()
        )

        AddTodoBottomSheet(
            todo = newTodo.value,
            showAddTodoBottomSheet = showAddTodoBottomSheet,
            onDismiss = { showAddTodoBottomSheet = false },
            onSave = {
                viewModel.addTodo()
                showAddTodoBottomSheet = false
            },
            onTodoChange = viewModel::setNewTodo
        )

        EditTodoBottomSheet(todo = selectedTodo.value,
            onTodoChange = viewModel::setSelectedTodo,
            onSave = {
                viewModel.updateTodo()
                showEditTodoBottomSheet = false
            },
            showEditTodoBottomSheet = showEditTodoBottomSheet,
            onDismiss = { showEditTodoBottomSheet = false })
    }
}

@Composable
fun TodosList(
    todos: List<Todo>,
    modifier: Modifier = Modifier,
    onTodoClick: (Int) -> Unit,
    onIsDoneChange: (id: Int, isDone: Boolean) -> Unit,
    onRemove: (Todo) -> Unit
) {


    LazyColumn(modifier = modifier.fillMaxWidth()) {
        items(items = todos, key = { it.id }) {

            TodosListItem(
                todo = it,
                modifier = Modifier.fillMaxWidth(),
                onClick = onTodoClick,
                onIsDoneChange = onIsDoneChange,
                onRemove = onRemove
            )
        }
    }
}

@Composable
fun TodosListItem(
    todo: Todo,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit,
    onIsDoneChange: (id: Int, isDone: Boolean) -> Unit,
    onRemove: (Todo) -> Unit
) {
    Card(modifier = modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            Checkbox(
                checked = todo.isDone, onCheckedChange = {
                    onIsDoneChange(todo.id, it)
                }, modifier = Modifier.padding(end = 8.dp)
            )
            Text(text = todo.todo, modifier = Modifier
                .weight(1f)
                .clickable { onClick(todo.id) })
            IconButton(onClick = { onRemove(todo) }) {
                Icon(
                    imageVector = Icons.Rounded.RemoveCircleOutline,
                    contentDescription = "Remove todo"
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 450)
@Composable
fun TodosListPreview() {
    TodosList(todos = DataSource.Todos,
        onTodoClick = {},
        onIsDoneChange = { _, _ -> },
        onRemove = {})
}
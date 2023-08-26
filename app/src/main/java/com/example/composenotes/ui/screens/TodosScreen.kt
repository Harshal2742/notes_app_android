package com.example.composenotes.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodosList(
    todos: List<Todo>,
    modifier: Modifier = Modifier,
    onTodoClick: (Int) -> Unit,
    onIsDoneChange: (id: Int, isDone: Boolean) -> Unit,
    onRemove: (Todo) -> Unit
) {

    LazyColumn(modifier = modifier.fillMaxWidth()) {
        items(items = todos, key = { it.id }) { todo ->
            val dismissState = rememberDismissState(
                confirmValueChange = { dismissValue ->
                    if (dismissValue == DismissValue.DismissedToEnd) {
                        onRemove(todo)
                    }
                    return@rememberDismissState true
                },
                positionalThreshold = { swipeActivationFloat -> swipeActivationFloat / 2 }
            )

            SwipeToDismiss(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clip(RoundedCornerShape(14.dp)),
                state = dismissState,
                background = { DismissBackground() },
                dismissContent = {
                    TodosListItem(
                        todo = todo,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onTodoClick,
                        onIsDoneChange = onIsDoneChange
                    )
                },
                directions = setOf(DismissDirection.StartToEnd)
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
) {
    Card(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            Checkbox(
                checked = todo.isDone, onCheckedChange = {
                    onIsDoneChange(todo.id, it)
                }, modifier = Modifier.padding(end = 8.dp)
            )
            Text(text = todo.todo, modifier = Modifier
                .weight(1f)
                .clickable { onClick(todo.id) })
        }
    }
}

@Composable
fun DismissBackground(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.errorContainer)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            Spacer(modifier = Modifier.width(40.dp))
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = null,
                modifier = Modifier
                    .width(28.dp)
                    .height(28.dp)
            )
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
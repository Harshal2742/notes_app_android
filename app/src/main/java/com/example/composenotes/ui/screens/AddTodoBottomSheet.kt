package com.example.composenotes.ui.screens

import androidx.compose.runtime.Composable
import com.example.composenotes.data.Todo


@Composable
fun AddTodoBottomSheet(
    todo: Todo,
    showAddTodoBottomSheet: Boolean,
    onDismiss: () -> Unit,
    onSave: () -> Unit,
    onTodoChange: (String) -> Unit
) {

    TodoScreenLayout(
        todo = todo,
        showBottomSheet = showAddTodoBottomSheet,
        onDismiss = onDismiss,
        onSave = onSave,
        onTodoChange = onTodoChange
    )
}
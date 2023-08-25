package com.example.composenotes.ui.screens

import androidx.compose.runtime.Composable
import com.example.composenotes.data.Todo


@Composable
fun EditTodoBottomSheet(
    todo: Todo,
    onTodoChange: (String) -> Unit,
    onSave: () -> Unit,
    showEditTodoBottomSheet: Boolean,
    onDismiss: () -> Unit
) {
    TodoScreenLayout(
        todo = todo,
        showBottomSheet = showEditTodoBottomSheet,
        onDismiss = onDismiss,
        onSave = onSave,
        onTodoChange = onTodoChange,
    )
}
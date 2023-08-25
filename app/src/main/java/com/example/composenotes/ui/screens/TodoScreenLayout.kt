package com.example.composenotes.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.composenotes.data.Todo
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreenLayout(
    todo: Todo,
    showBottomSheet: Boolean,
    onDismiss: () -> Unit,
    onSave: () -> Unit,
    onTodoChange: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState()
    val focusRequester = remember { FocusRequester() }


    if (showBottomSheet) {
        LaunchedEffect(key1 = Unit) {
            focusRequester.requestFocus()
        }
        ModalBottomSheet(
            sheetState = bottomSheetState,
            modifier = Modifier.fillMaxSize(1f),
            onDismissRequest = {
                coroutineScope.launch {
                    bottomSheetState.hide()
                }.invokeOnCompletion {
                    onDismiss()
                }
            }) {
            OutlinedTextField(
                value = todo.todo, onValueChange = onTodoChange,
                placeholder = {
                    Text(
                        text = "Write to-do",
                        color = Color.Gray,
                        fontWeight = FontWeight.Normal,
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Unspecified,
                    unfocusedBorderColor = Color.Unspecified,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
            )

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
            ) {


                TextButton(onClick = onSave) {
                    Text(text = "Save")
                }
            }
        }
    }
}

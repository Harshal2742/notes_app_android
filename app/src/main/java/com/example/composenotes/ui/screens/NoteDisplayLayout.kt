package com.example.composenotes.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDisplayLayout(
    date: String,
    title: String?,
    description: String,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(horizontal = 8.dp, vertical = 8.dp)) {
        Text(
            text = date,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = Color.Gray
        )
        OutlinedTextField(
            value = title ?: "",
            onValueChange = onTitleChange,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Unspecified,
                unfocusedBorderColor = Color.Unspecified,
            ),
            placeholder = {
                Text(
                    text = "Title",
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray,
                    fontSize = 20.sp
                )
            },
            textStyle = TextStyle(fontWeight = FontWeight.Medium, fontSize = 20.sp),
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(
            modifier = modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(color = Color.LightGray)
        )
        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            placeholder = {
                Text(
                    text = "Enter description",
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Unspecified,
                unfocusedBorderColor = Color.Unspecified,
            )
        )

    }
}
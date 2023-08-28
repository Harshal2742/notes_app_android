package com.example.composenotes.presentation.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.composenotes.R
import com.example.composenotes.ui.ScaffoldState


@Composable
fun SignInScreen(
    onSignIn: () -> Unit,
    onComposing: (ScaffoldState) -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        onComposing(
            ScaffoldState(
                showBottomNavigation = false
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            onSignIn()
        }) {
            Image(
                painter = painterResource(R.drawable.google_icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Sign in", fontWeight = FontWeight.SemiBold)
        }
    }
}
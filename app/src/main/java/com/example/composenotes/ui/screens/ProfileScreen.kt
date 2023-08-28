package com.example.composenotes.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.composenotes.R
import com.example.composenotes.presentation.authentication.UserData
import com.example.composenotes.ui.NotesAppScreen
import com.example.composenotes.ui.ScaffoldState

@Composable
fun ProfileScreen(
    onComposing: (ScaffoldState) -> Unit,
    modifier: Modifier = Modifier,
    onSignOut: () -> Unit,
    userData: UserData
) {

    val context = LocalContext.current



    LaunchedEffect(key1 = Unit) {
        onComposing(
            ScaffoldState(
                topAppBarTitle = context.getString(NotesAppScreen.Profile.title),
                showBottomNavigation = true
            )
        )
    }

    Column(modifier = modifier.fillMaxSize()) {

        ProfilePictureCard(
            profileImgUri = userData.profilePicture ?: "",
            userName = userData.userName ?: "",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                onSignOut()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp
                )
        ) {
            Text(text = "Sign out")
        }

    }
}

@Composable
fun ProfilePictureCard(profileImgUri: String, userName: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(horizontal = 16.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(profileImgUri)
                    .crossfade(true).build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(75.dp),
                placeholder = painterResource(id = R.drawable.profile_placeholder)
            )
            Spacer(modifier = modifier.height(12.dp))
            Text(
                text = userName,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

    }
}
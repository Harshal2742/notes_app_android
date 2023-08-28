package com.example.composenotes.presentation.authentication


data class SignInResult(
    val data: UserData?,
    val error: String?
)

data class UserData(
    val userId: String,
    val userName: String?,
    val profilePicture: String?
)

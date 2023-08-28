package com.example.composenotes.presentation.authentication

data class SignInState(
    val isSignInSuccessfully: Boolean = false,
    val signInError: String? = null
)

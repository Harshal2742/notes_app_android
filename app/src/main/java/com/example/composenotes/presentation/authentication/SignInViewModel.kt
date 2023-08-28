package com.example.composenotes.presentation.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

//@HiltViewModel
class SignInViewModel : ViewModel() {
    private val _state = MutableStateFlow(SignInState(isSignInSuccessfully = false))
    val state = _state.asStateFlow()

    fun onSignInResult(signInResult: SignInResult) {
        _state.update {
            it.copy(
                isSignInSuccessfully = signInResult.data != null,
                signInError = signInResult.error
            )
        }
    }

    fun resetState() {
        _state.update { SignInState() }
    }
}
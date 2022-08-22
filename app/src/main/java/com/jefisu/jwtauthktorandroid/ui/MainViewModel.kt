package com.jefisu.jwtauthktorandroid.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jefisu.jwtauthktorandroid.data.AuthResult
import com.jefisu.jwtauthktorandroid.data.repository.AuthRepository
import com.jefisu.jwtauthktorandroid.ui.auth.AuthState
import com.jefisu.jwtauthktorandroid.ui.auth.AuthUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(AuthState())
        private set

    private val _authChannel = Channel<AuthResult>()
    val authResults = _authChannel.receiveAsFlow()

    init {
        authenticate()
    }

    fun onEvent(event: AuthUiEvent) {
        when (event) {
            is AuthUiEvent.SignInLoginChanged -> {
                state = state.copy(signInLogin = event.value)
            }
            is AuthUiEvent.SignInPasswordChanged -> {
                state = state.copy(signInPassword = event.value)
            }
            is AuthUiEvent.SignIn -> {
                signIn()
            }
            is AuthUiEvent.SignUpUsernameChanged -> {
                state = state.copy(signUpUsername = event.value)
            }
            is AuthUiEvent.SignUpEmailChanged -> {
                state = state.copy(signUpEmail = event.value)
            }
            is AuthUiEvent.SignUpPasswordChanged -> {
                state = state.copy(signUpPassword = event.value)
            }
            is AuthUiEvent.SignUp -> {
                signUp()
            }
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.signUp(
                username = state.signUpUsername,
                email = state.signUpEmail,
                password = state.signUpPassword
            )
            _authChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }


    private fun signIn() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.signIn(
                login = state.signInLogin,
                password = state.signInPassword
            )
            _authChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }

    private fun authenticate() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.authenticate()
            _authChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }
}
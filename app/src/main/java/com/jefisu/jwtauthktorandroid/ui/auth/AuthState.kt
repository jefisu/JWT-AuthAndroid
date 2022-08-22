package com.jefisu.jwtauthktorandroid.ui.auth

data class AuthState(
    val isLoading: Boolean = false,
    val signUpUsername: String = "",
    val signUpEmail: String = "",
    val signUpPassword: String = "",
    val signInLogin: String = "",
    val signInPassword: String = ""
)
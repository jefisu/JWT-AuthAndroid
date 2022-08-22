package com.jefisu.jwtauthktorandroid.data.request

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    val login: String,
    val password: String
)
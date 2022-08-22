package com.jefisu.jwtauthktorandroid.data.response

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String
)
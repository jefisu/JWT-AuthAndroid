package com.jefisu.jwtauthktorandroid.data

sealed class AuthResult {
    data class Authorized(val message: String) : AuthResult()
    data class Unauthorized(val message: String) : AuthResult()
    object UnknownError : AuthResult()
}
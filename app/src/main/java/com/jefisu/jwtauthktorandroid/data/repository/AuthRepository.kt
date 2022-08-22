package com.jefisu.jwtauthktorandroid.data.repository

import com.jefisu.jwtauthktorandroid.data.AuthResult

interface AuthRepository {

    suspend fun signUp(
        username: String,
        email: String,
        password: String
    ): AuthResult

    suspend fun signIn(login: String, password: String): AuthResult
    suspend fun authenticate(): AuthResult
}
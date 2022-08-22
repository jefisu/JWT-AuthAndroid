package com.jefisu.jwtauthktorandroid.data.repository

import android.content.SharedPreferences
import com.jefisu.jwtauthktorandroid.data.AuthResult
import com.jefisu.jwtauthktorandroid.data.CustomResponseException
import com.jefisu.jwtauthktorandroid.data.request.SignInRequest
import com.jefisu.jwtauthktorandroid.data.request.SignUpRequest
import com.jefisu.jwtauthktorandroid.data.response.AuthResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val client: HttpClient,
    private val prefs: SharedPreferences
) : AuthRepository {

    override suspend fun signUp(
        username: String,
        email: String,
        password: String
    ): AuthResult {
        return try {
            val response = client.post("signup") {
                setBody(SignUpRequest(username, email, password))
            }
            signIn(username, password)
            AuthResult.Authorized(response.bodyAsText())
        } catch (e: CustomResponseException) {
            AuthResult.Unauthorized(e.message.toString())
        } catch (e: IOException) {
            AuthResult.Unauthorized("Check your internet connection.")
        }
    }

    override suspend fun signIn(login: String, password: String): AuthResult {
        return try {
            val response = client.post("signin") {
                setBody(SignInRequest(login, password))
            }
            prefs.edit()
                .putString("jwt", response.body<AuthResponse>().token)
                .apply()
            AuthResult.Authorized("Login successfully performed.")
        } catch (e: CustomResponseException) {
            AuthResult.Unauthorized(e.message.toString())
        } catch (e: IOException) {
            AuthResult.Unauthorized("Check your internet connection.")
        }
    }

    override suspend fun authenticate(): AuthResult {
        return try {
            val token = prefs.getString("jwt", null) ?: return AuthResult.UnknownError
            val response = client.get("authenticate") {
                header(HttpHeaders.Authorization, "Bearer $token")
            }
            AuthResult.Authorized(response.bodyAsText())
        } catch (e: CustomResponseException) {
            AuthResult.Unauthorized(e.message.toString())
        } catch (e: IOException) {
            AuthResult.Unauthorized("Check your internet connection.")
        }
    }
}
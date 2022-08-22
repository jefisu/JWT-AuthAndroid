package com.jefisu.jwtauthktorandroid.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.jefisu.jwtauthktorandroid.data.CustomResponseException
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                json()
            }
            install(Logging) {
                level = LogLevel.ALL
            }
            defaultRequest {
                url("http://192.168.0.2:8080/")
                contentType(ContentType.Application.Json)
            }
            HttpResponseValidator {
                validateResponse { response ->
                    if (response.status.value != 200) {
                        throw CustomResponseException(response.bodyAsText())
                    }
                }
            }
        }
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }
}
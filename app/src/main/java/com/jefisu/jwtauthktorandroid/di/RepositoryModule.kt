package com.jefisu.jwtauthktorandroid.di

import com.jefisu.jwtauthktorandroid.data.repository.AuthRepository
import com.jefisu.jwtauthktorandroid.data.repository.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        repositoryImpl: AuthRepositoryImpl
    ): AuthRepository
}
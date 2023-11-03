package com.eem.data.di

import com.eem.data.network.api.AuthApi
import com.eem.data.network.model.CoroutineContextProvider
import com.eem.data.repository.AuthRepositoryImpl
import com.eem.domain.repository.auth.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideCoroutineContextProvider(): CoroutineContextProvider {
        return CoroutineContextProvider()
    }

    @Provides
    fun provideAuthRepository(
        authApi: AuthApi,
        contextProvider: CoroutineContextProvider
    ): AuthRepository = AuthRepositoryImpl(authApi, contextProvider)
}

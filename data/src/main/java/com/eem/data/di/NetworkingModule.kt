package com.eem.data.di

import com.eem.data.network.api.AuthApi
import com.eem.data.network.api.UserApi
import com.eem.data.network.buildApi
import com.eem.data.network.getLoggerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    @Singleton
    @Provides
    fun provideAuthenticationApi(): AuthApi =
        buildApi(
            interceptors = listOf(getLoggerInterceptor()),
            api = AuthApi::class.java
        )

    @Singleton
    @Provides
    fun provideUserApi(): UserApi =
        buildApi(
            interceptors = listOf(getLoggerInterceptor()),
            api = UserApi::class.java
        )
}
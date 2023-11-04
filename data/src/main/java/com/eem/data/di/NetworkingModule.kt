package com.eem.data.di

import com.eem.data.network.AuthenticationNimble
import com.eem.data.network.api.AuthApi
import com.eem.data.network.api.SurveyApi
import com.eem.data.network.api.UserApi
import com.eem.data.network.buildApi
import com.eem.data.network.getLoggerInterceptor
import com.eem.data.room.dao.AccessTokenDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
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

    @Singleton
    @Provides
    fun provideSurveyApiApi(
        accessTokenDao: AccessTokenDao,
        authApi: AuthApi,
        authenticator: Authenticator
    ): SurveyApi =
        buildApi(
            interceptors = listOf(getLoggerInterceptor()),
            api = SurveyApi::class.java,
            authenticator = authenticator
        )

    @Singleton
    @Provides
    fun provideAuthenticator(accessTokenDao: AccessTokenDao, authApi: AuthApi): Authenticator =
        AuthenticationNimble(accessTokenDao, authApi)
}
package com.eem.data.di

import com.eem.data.network.api.AuthApi
import com.eem.data.network.api.SurveyApi
import com.eem.data.network.api.UserApi
import com.eem.data.network.model.CoroutineContextProvider
import com.eem.data.repository.AuthRepositoryImpl
import com.eem.data.repository.SurveyRepositoryImpl
import com.eem.data.repository.UserRepositoryImpl
import com.eem.data.room.dao.AccessTokenDao
import com.eem.domain.repository.auth.AuthRepository
import com.eem.domain.repository.survey.SurveyRepository
import com.eem.domain.repository.user.UserRepository
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
        accessTokenDao: AccessTokenDao,
        contextProvider: CoroutineContextProvider
    ): AuthRepository = AuthRepositoryImpl(authApi, accessTokenDao, contextProvider)

    @Provides
    fun provideSurveyRepository(
        surveyApi: SurveyApi,
        accessTokenDao: AccessTokenDao,
        contextProvider: CoroutineContextProvider
    ): SurveyRepository = SurveyRepositoryImpl(surveyApi, accessTokenDao, contextProvider)

    @Provides
    fun provideUserRepository(
        userApi: UserApi,
        accessTokenDao: AccessTokenDao,
        contextProvider: CoroutineContextProvider
    ): UserRepository = UserRepositoryImpl(userApi, accessTokenDao, contextProvider)
}

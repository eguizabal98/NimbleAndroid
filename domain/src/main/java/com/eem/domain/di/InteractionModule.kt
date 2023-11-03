package com.eem.domain.di

import com.eem.domain.interactor.auth.ForgotPasswordUseCase
import com.eem.domain.interactor.auth.ForgotPasswordUseCaseImpl
import com.eem.domain.interactor.auth.LogOutUseCase
import com.eem.domain.interactor.auth.LogOutUseCaseImpl
import com.eem.domain.interactor.auth.LoginUserUseCase
import com.eem.domain.interactor.auth.LoginUserUseCaseImpl
import com.eem.domain.interactor.auth.RegisterUserUseCase
import com.eem.domain.interactor.auth.RegisterUserUseCaseImpl
import com.eem.domain.repository.auth.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object InteractionModule {

    @Provides
    fun provideRegisterUserUseCase(authRepository: AuthRepository): RegisterUserUseCase =
        RegisterUserUseCaseImpl(authRepository)

    @Provides
    fun provideLogInUseCase(authRepository: AuthRepository): LoginUserUseCase =
        LoginUserUseCaseImpl(authRepository)

    @Provides
    fun provideLogOutUseCase(authRepository: AuthRepository): LogOutUseCase =
        LogOutUseCaseImpl(authRepository)

    @Provides
    fun provideForgotPasswordUseCase(authRepository: AuthRepository): ForgotPasswordUseCase =
        ForgotPasswordUseCaseImpl(authRepository)
}

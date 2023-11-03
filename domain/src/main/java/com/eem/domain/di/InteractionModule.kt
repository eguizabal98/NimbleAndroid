package com.eem.domain.di

import com.eem.domain.interactor.auth.ForgotPasswordUseCase
import com.eem.domain.interactor.auth.ForgotPasswordUseCaseImpl
import com.eem.domain.interactor.auth.IsUserLoginUseCase
import com.eem.domain.interactor.auth.IsUserLoginUseCaseImpl
import com.eem.domain.interactor.auth.LogOutUseCase
import com.eem.domain.interactor.auth.LogOutUseCaseImpl
import com.eem.domain.interactor.auth.LoginUserUseCase
import com.eem.domain.interactor.auth.LoginUserUseCaseImpl
import com.eem.domain.interactor.auth.RegisterUserUseCase
import com.eem.domain.interactor.auth.RegisterUserUseCaseImpl
import com.eem.domain.interactor.survey.GetSurveyDetailsUseCase
import com.eem.domain.interactor.survey.GetSurveyDetailsUseCaseImpl
import com.eem.domain.interactor.survey.GetSurveyListUseCase
import com.eem.domain.interactor.survey.GetSurveyListUseCaseImpl
import com.eem.domain.interactor.survey.SubmitSurveyUseCase
import com.eem.domain.interactor.survey.SubmitSurveyUseCaseImpl
import com.eem.domain.interactor.user.GetProfileInfoUseCase
import com.eem.domain.interactor.user.GetProfileInfoUseCaseImpl
import com.eem.domain.repository.auth.AuthRepository
import com.eem.domain.repository.survey.SurveyRepository
import com.eem.domain.repository.user.UserRepository
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

    @Provides
    fun provideIsUserLoginUseCase(authRepository: AuthRepository): IsUserLoginUseCase =
        IsUserLoginUseCaseImpl(authRepository)

    @Provides
    fun provideGetSurveyDetailsUseCase(surveyRepository: SurveyRepository): GetSurveyDetailsUseCase =
        GetSurveyDetailsUseCaseImpl(surveyRepository)

    @Provides
    fun provideGetSurveyListUseCase(surveyRepository: SurveyRepository): GetSurveyListUseCase =
        GetSurveyListUseCaseImpl(surveyRepository)

    @Provides
    fun provideSubmitSurveyUseCase(surveyRepository: SurveyRepository): SubmitSurveyUseCase =
        SubmitSurveyUseCaseImpl(surveyRepository)

    @Provides
    fun provideGetProfileInfoUseCase(userRepository: UserRepository): GetProfileInfoUseCase =
        GetProfileInfoUseCaseImpl(userRepository)
}

package com.eem.domain.interactor.auth

import com.eem.domain.model.base.ResultWrapper
import com.eem.domain.repository.auth.AuthRepository
import javax.inject.Inject

class ForgotPasswordUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : ForgotPasswordUseCase {
    override suspend fun invoke(email: String): ResultWrapper<String> =
        authRepository.forgotPassword(email)
}

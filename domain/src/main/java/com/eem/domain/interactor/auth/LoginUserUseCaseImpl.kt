package com.eem.domain.interactor.auth

import com.eem.domain.model.auth.LoginUser
import com.eem.domain.model.base.ResultWrapper
import com.eem.domain.repository.auth.AuthRepository
import javax.inject.Inject

class LoginUserUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
): LoginUserUseCase {
    override suspend fun invoke(loginUser: LoginUser): ResultWrapper<Boolean> =
        authRepository.loginUser(loginUser)
}
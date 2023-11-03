package com.eem.domain.interactor.auth

import com.eem.domain.model.auth.RegisterUser
import com.eem.domain.model.base.ResultWrapper
import com.eem.domain.repository.auth.AuthRepository
import javax.inject.Inject

class RegisterUserUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : RegisterUserUseCase {
    override suspend fun invoke(registerUser: RegisterUser): ResultWrapper<Boolean> =
        authRepository.registerUser(registerUser)
}

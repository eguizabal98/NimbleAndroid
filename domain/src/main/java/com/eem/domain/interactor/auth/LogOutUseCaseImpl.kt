package com.eem.domain.interactor.auth

import com.eem.domain.model.base.ResultWrapper
import com.eem.domain.repository.auth.AuthRepository
import javax.inject.Inject

class LogOutUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
): LogOutUseCase {
    override suspend fun invoke(): ResultWrapper<Boolean> = authRepository.logOut()
}
package com.eem.domain.interactor.auth

import com.eem.domain.model.base.ResultWrapper

interface ForgotPasswordUseCase {
    suspend operator fun invoke(email: String): ResultWrapper<String>
}
package com.eem.domain.interactor.auth

import com.eem.domain.model.auth.RegisterUser
import com.eem.domain.model.base.ResultWrapper

interface RegisterUserUseCase {
    suspend operator fun invoke(registerUser: RegisterUser): ResultWrapper<Boolean>
}

package com.eem.domain.interactor.auth

import com.eem.domain.model.auth.LoginUser
import com.eem.domain.model.base.ResultWrapper

interface LoginUserUseCase {
    suspend operator fun invoke(loginUser: LoginUser): ResultWrapper<Boolean>
}
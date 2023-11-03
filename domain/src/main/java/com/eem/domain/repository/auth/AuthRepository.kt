package com.eem.domain.repository.auth

import com.eem.domain.model.auth.LoginUser
import com.eem.domain.model.auth.RegisterUser
import com.eem.domain.model.base.ResultWrapper

interface AuthRepository {
    suspend fun registerUser(registerUser: RegisterUser): ResultWrapper<Boolean>
    suspend fun loginUser(loginUser: LoginUser): ResultWrapper<Boolean>
    suspend fun logOut(): ResultWrapper<Boolean>
    suspend fun forgotPassword(email: String): ResultWrapper<String>
}

package com.eem.data.repository

import com.eem.data.network.api.AuthApi
import com.eem.data.network.model.CoroutineContextProvider
import com.eem.data.network.model.auth.ForgotPasswordBody
import com.eem.data.network.model.auth.LogOutBody
import com.eem.data.network.model.auth.LoginUserBody
import com.eem.data.network.model.auth.RegisterUserBody
import com.eem.data.network.model.auth.UserBody
import com.eem.data.network.model.auth.UserEmail
import com.eem.domain.model.auth.LoginUser
import com.eem.domain.model.auth.RegisterUser
import com.eem.domain.model.base.ResultWrapper
import com.eem.domain.repository.auth.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val contextProvider: CoroutineContextProvider
) : AuthRepository {

    override suspend fun registerUser(registerUser: RegisterUser): ResultWrapper<Boolean> =
        executeApiCall(
            contextProvider = contextProvider,
            call = {
                authApi.registerUser(
                    RegisterUserBody(
                        UserBody(
                            registerUser.email,
                            registerUser.name,
                            registerUser.password,
                            registerUser.passwordConfirmation
                        )
                    )
                )
            },
            resultAction = {
                true
            }
        )

    override suspend fun loginUser(loginUser: LoginUser): ResultWrapper<Boolean> =
        executeApiCall(
            contextProvider = contextProvider,
            call = {
                authApi.loginUser(
                    LoginUserBody(
                        email = loginUser.email,
                        password = loginUser.password
                    )
                )
            },
            resultAction = {
                true
            }
        )

    override suspend fun logOut(): ResultWrapper<Boolean> =
        executeApiCall(
            contextProvider = contextProvider,
            call = {
                authApi.logOut(LogOutBody(""))
            },
            resultAction = {
                true
            }
        )

    override suspend fun forgotPassword(email: String): ResultWrapper<String> =
        executeApiCall(
            contextProvider = contextProvider,
            call = {
                authApi.forgotPassword(ForgotPasswordBody(UserEmail(email)))
            },
            resultAction = {
                it.meta.message
            }
        )
}
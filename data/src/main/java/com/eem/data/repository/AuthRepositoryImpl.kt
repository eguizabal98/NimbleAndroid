package com.eem.data.repository

import com.eem.data.network.api.AuthApi
import com.eem.data.network.model.CoroutineContextProvider
import com.eem.data.network.model.auth.ForgotPasswordBody
import com.eem.data.network.model.auth.LogOutBody
import com.eem.data.network.model.auth.LoginUserBody
import com.eem.data.network.model.auth.RegisterUserBody
import com.eem.data.network.model.auth.UserBody
import com.eem.data.network.model.auth.UserEmail
import com.eem.data.room.dao.AccessTokenDao
import com.eem.data.room.model.AccessTokenEntity
import com.eem.domain.model.auth.LoginUser
import com.eem.domain.model.auth.RegisterUser
import com.eem.domain.model.base.ResultWrapper
import com.eem.domain.repository.auth.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val accessTokenDao: AccessTokenDao,
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
                accessTokenDao.clear()
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
                accessTokenDao.insertAll(
                    AccessTokenEntity(
                        it.data.attributes.accessToken,
                        it.data.attributes.tokenType,
                        it.data.attributes.expiresIn,
                        it.data.attributes.refreshToken,
                        it.data.attributes.createdAt
                    )
                )
                true
            }
        )

    override suspend fun logOut(): ResultWrapper<Boolean> =
        executeApiCall(
            contextProvider = contextProvider,
            call = {
                authApi.logOut(LogOutBody(accessTokenDao.getAll()?.first()?.accessToken))
            },
            resultAction = {
                accessTokenDao.clear()
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
                accessTokenDao.clear()
                it.meta.message
            }
        )
}
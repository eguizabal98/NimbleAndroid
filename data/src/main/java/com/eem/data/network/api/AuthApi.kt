package com.eem.data.network.api

import com.eem.data.network.model.EmptyResponse
import com.eem.data.network.model.GeneralMessage
import com.eem.data.network.model.auth.ForgotPasswordBody
import com.eem.data.network.model.auth.LogOutBody
import com.eem.data.network.model.auth.LoginUserBody
import com.eem.data.network.model.auth.RefreshTokenBody
import com.eem.data.network.model.auth.RegisterUserBody
import com.eem.data.network.model.auth.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("api/v1/registrations")
    suspend fun registerUser(
        @Body registerUserBody: RegisterUserBody
    ): Response<EmptyResponse>

    @POST("api/v1/oauth/token")
    suspend fun loginUser(
        @Body loginUserBody: LoginUserBody
    ): Response<TokenResponse>

    @POST("api/v1/oauth/token")
    suspend fun refreshToken(
        @Body refreshTokenBody: RefreshTokenBody
    ): Response<TokenResponse>

    @POST("api/v1/oauth/revoke")
    suspend fun logOut(
        @Body logOutBody: LogOutBody
    ): Response<EmptyResponse>

    @POST("api/v1/passwords")
    suspend fun forgotPassword(
        @Body forgotPasswordBody: ForgotPasswordBody
    ): Response<GeneralMessage>
}

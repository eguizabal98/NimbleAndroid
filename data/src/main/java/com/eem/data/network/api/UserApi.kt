package com.eem.data.network.api

import com.eem.data.network.model.user.ProfileInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface UserApi {

    @GET("api/v1/me")
    suspend fun getProfileInfo(
        @Header("Authorization") authorization: String
    ): Response<ProfileInfoResponse>
}

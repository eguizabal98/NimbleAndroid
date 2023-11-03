package com.eem.data.network.model.auth

import com.eem.data.network.model.Credentials
import com.google.gson.annotations.SerializedName

data class RefreshTokenBody(
    @SerializedName("grant_type")
    val grantType: String = "refresh_token",
    @SerializedName("refresh_token")
    val refreshToken: String
) : Credentials()

package com.eem.data.network.model.auth

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("data")
    val data: LoginData
)

data class LoginData(
    @SerializedName("id")
    val id: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("attributes")
    val attributes: LoginAttributes
)

data class LoginAttributes(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("expires_in")
    val expiresIn: Long,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("created_at")
    val createdAt: Long
)

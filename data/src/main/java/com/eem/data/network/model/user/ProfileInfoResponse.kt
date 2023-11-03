package com.eem.data.network.model.user

import com.google.gson.annotations.SerializedName

data class ProfileInfoResponse(
    @SerializedName("data")
    val data: ProfileData
)

data class ProfileData(
    @SerializedName("id")
    val id: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("attributes")
    val attributes: UserAttributes
)

data class UserAttributes(
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)

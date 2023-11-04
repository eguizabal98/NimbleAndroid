package com.eem.data.network.model.auth

import com.eem.data.network.model.Credentials
import com.google.gson.annotations.SerializedName

data class LoginUserBody(
    @SerializedName("grant_type")
    val grantType: String = "password",
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
) : Credentials()

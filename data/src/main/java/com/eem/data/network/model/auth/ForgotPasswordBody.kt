package com.eem.data.network.model.auth

import com.eem.data.network.model.Credentials
import com.google.gson.annotations.SerializedName

data class ForgotPasswordBody(
    @SerializedName("user")
    val user: UserEmail
) : Credentials()

data class UserEmail(
    @SerializedName("email")
    val email: String
)
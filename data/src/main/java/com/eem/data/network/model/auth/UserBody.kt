package com.eem.data.network.model.auth

import com.google.gson.annotations.SerializedName

data class UserBody(
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("password_confirmation")
    val passwordConfirmation: String
)

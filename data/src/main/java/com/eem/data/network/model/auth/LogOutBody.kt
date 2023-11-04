package com.eem.data.network.model.auth

import com.eem.data.network.model.Credentials
import com.google.gson.annotations.SerializedName

data class LogOutBody(
    @SerializedName("token")
    val token: String?,
) : Credentials()

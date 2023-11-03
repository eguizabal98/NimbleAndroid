package com.eem.data.network.model

import com.eem.data.BuildConfig
import com.google.gson.annotations.SerializedName

open class Credentials {
    @SerializedName("client_id")
    val clientId: String = BuildConfig.API_KEY
    @SerializedName("client_secret")
    val clientSecret: String = BuildConfig.SECRET
}
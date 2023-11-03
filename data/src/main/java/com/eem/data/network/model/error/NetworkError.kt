package com.eem.data.network.model.error

import com.google.gson.annotations.SerializedName

data class NetworkError(
    @SerializedName("detail")
    val detail: String = "",
    @SerializedName("code")
    val code: String = ""
)

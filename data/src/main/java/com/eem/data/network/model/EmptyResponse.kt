package com.eem.data.network.model

import com.google.gson.annotations.SerializedName

data class EmptyResponse(
    @SerializedName("code")
    val statusCode: Int? = 0
)

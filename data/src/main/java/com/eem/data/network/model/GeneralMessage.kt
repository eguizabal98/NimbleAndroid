package com.eem.data.network.model

import com.google.gson.annotations.SerializedName

data class GeneralMessage(
    @SerializedName("meta")
    val meta: MetaInfo
)

data class MetaInfo(
    @SerializedName("message")
    val message: String = ""
)

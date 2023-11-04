package com.eem.data.network.model.error

import com.google.gson.annotations.SerializedName

data class ErrorList(
    @SerializedName("errors")
    val errorsList: List<NetworkError>
)

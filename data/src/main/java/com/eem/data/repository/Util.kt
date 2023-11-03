package com.eem.data.repository

import com.eem.data.network.model.CoroutineContextProvider
import com.eem.data.network.model.error.ErrorList
import com.eem.domain.model.base.Failure
import com.eem.domain.model.base.HttpError
import com.eem.domain.model.base.ResultWrapper
import com.eem.domain.model.base.Success
import com.google.gson.Gson
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend fun <R : Response<T>, T, W> executeApiCall(
    contextProvider: CoroutineContextProvider,
    call: suspend () -> R,
    resultAction: (T) -> W
): ResultWrapper<W> {
    val result = withContext(contextProvider.io) {
        call()
    }
    return if (result.isSuccessful) {
        result.body()?.let {
            Success(resultAction(it))
        } ?: run {
            Failure(HttpError(""))
        }
    } else {
        val errors = Gson().fromJson(result.errorBody()?.string(), ErrorList::class.java)
        Failure(HttpError(errors.errorsList.first().detail, result.code()))
    }
}

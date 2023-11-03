package com.eem.domain.model.base

sealed class ResultWrapper<out T>

object Loading : ResultWrapper<Nothing>()
data class Success<out T>(val data: T) : ResultWrapper<T>()
data class Failure(val httpError: HttpError) : ResultWrapper<Nothing>()

class HttpError(val message: String, val errorCode: Int = 0)

inline fun <T> ResultWrapper<T>.onSuccess(action: (T) -> Unit): ResultWrapper<T> {
    if (this is Success) action(data)
    return this
}

inline fun <T> ResultWrapper<T>.onFailure(action: (HttpError) -> Unit) {
    if (this is Failure) action(httpError)
}
package com.desabulila.snapcencus.data

sealed class Result<T>(
    val data: T? = null, val error: Throwable? = null
) {
    class Success<T>(data: T) : Result<T>(data)
    class Error<T>(data: T? = null, throwable: Throwable) : Result<T>(data, throwable)
    class Loading<T> : Result<T>()
}
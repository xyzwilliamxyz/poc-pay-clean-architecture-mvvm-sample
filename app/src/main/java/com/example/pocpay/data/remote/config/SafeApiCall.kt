package com.example.pocpay.data.remote.config

import android.util.Log

suspend inline fun <T> safeApiCall(
    crossinline block: suspend () -> T
): ApiResult<T> {
    return try {
        val data = block()
        ApiResult.Success(data)
    } catch (ex: Exception) {
        Log.e("safeApiCall", ex.message ?: "")
        ApiResult.Failure(ex)
    }
}
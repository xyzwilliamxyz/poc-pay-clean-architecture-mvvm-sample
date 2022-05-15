package com.example.pocpay.feature.transaction.domain.interactor

sealed class Result<out H> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val error: String) : Result<Nothing>()
}
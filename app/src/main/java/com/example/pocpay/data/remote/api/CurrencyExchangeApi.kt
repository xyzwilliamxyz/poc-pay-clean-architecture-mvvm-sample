package com.example.pocpay.data.remote.api

import com.example.pocpay.data.remote.response.CurrencyExchangeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyExchangeApi {

    @GET("latest.json")
    suspend fun getLatest(
        @Query("currencyCode") currencyCode: String = "USD"
    ): CurrencyExchangeResponse
}
package com.example.pocpay.data.remote.response

data class CurrencyExchangeResponse(
    val base: String,
    val rates: Map<String, Double>
)

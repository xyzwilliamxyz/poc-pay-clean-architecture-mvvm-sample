package com.example.pocpay.data.remote.mapper

import com.example.pocpay.domain.model.CurrencyExchange
import javax.inject.Inject


class CurrencyExchangeMapper @Inject constructor() {

    fun toCurrency(response: Map<String, Double>): Map<String, CurrencyExchange> {
        return response.mapValues {
            CurrencyExchange(it.key, it.value)
        }
    }
}
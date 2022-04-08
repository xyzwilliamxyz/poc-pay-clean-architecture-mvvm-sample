package com.example.pocpay.data.remote.repository

import com.example.pocpay.domain.model.CurrencyExchange
import com.example.pocpay.data.remote.api.CurrencyExchangeApi
import com.example.pocpay.data.remote.mapper.CurrencyExchangeMapper
import javax.inject.Inject
import kotlinx.coroutines.flow.flow

class CurrencyExchangeRepository @Inject constructor(
    private val mapper: CurrencyExchangeMapper,
    private val api: CurrencyExchangeApi
) {

    suspend fun getCurrencies(): Map<String, CurrencyExchange> {
        val response = api.getLatest()
        return mapper.toCurrency(response.rates)
    }

    suspend fun getCurrenciesAlternateTwo() = flow {
        val response = api.getLatest()
        emit(mapper.toCurrency(response.rates))
    }
}
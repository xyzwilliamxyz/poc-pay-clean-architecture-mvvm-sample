package com.example.pocpay.feature.transaction.domain.interactor

import android.util.Log
import com.example.pocpay.core.extensions.TAG
import com.example.pocpay.core.exception.BusinessException
import com.example.pocpay.feature.transaction.domain.model.CurrencyExchange
import com.example.pocpay.data.remote.config.ApiResult
import com.example.pocpay.data.remote.config.safeApiCall
import com.example.pocpay.data.remote.repository.CurrencyExchangeRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transform


class CurrencyExchangeUseCase @Inject constructor(
    private val repository: CurrencyExchangeRepository
) {

    suspend fun getCurrencyExchangeByCode(transferValue: Double, currencyCode: String) = flow {
        val result = safeApiCall { repository.getCurrencies() }

        when (result) {
            is ApiResult.Success -> {
                val exchangeValue = transferValue * (result.data[currencyCode]?.value ?: 0.0)
                if (exchangeValue == 0.0) {
                    emit(Result.Failure("ERROR"))
                } else {
                    emit(
                        Result.Success(CurrencyExchange(currencyCode = currencyCode, value = exchangeValue))
                    )
                }
            }
            is ApiResult.Failure -> {
                Log.e(TAG(), LOG_MSG)
                emit(Result.Failure("ERROR"))
            }
        }
    }

    suspend fun getCurrencyExchangeByCodeAlternate(transferValue: Double, currencyCode: String) = flow {
        val result = repository.getCurrencies()
        val exchangeValue = transferValue * (result[currencyCode]?.value ?: 0.0)
        if (exchangeValue == 0.0) throw BusinessException("ERROR")

        emit(
            CurrencyExchange(currencyCode = currencyCode, value = exchangeValue)
        )
    }

    suspend fun getCurrencyExchangeByCodeAlternateTwo(transferValue: Double, currencyCode: String): Flow<CurrencyExchange> {
        return repository.getCurrenciesAlternateTwo()
            .transform { response ->
                val exchangeValue = transferValue * (response[currencyCode]?.value ?: 0.0)
                if (exchangeValue == 0.0) throw BusinessException("ERROR")

                emit(
                    CurrencyExchange(currencyCode = currencyCode, value = exchangeValue)
                )
            }
    }

    companion object {
        private const val LOG_MSG = "error fetching exchange code"
    }
}

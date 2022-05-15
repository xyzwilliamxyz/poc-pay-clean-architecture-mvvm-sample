package com.example.pocpay.feature.transaction.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocpay.core.ui.UiEvent
import com.example.pocpay.di.CoroutineDispatcherProvider
import com.example.pocpay.feature.transaction.contract.AddTransactionContract
import com.example.pocpay.feature.transaction.contract.AddTransactionContract.Interaction.OnBackClick
import com.example.pocpay.feature.transaction.contract.AddTransactionContract.Interaction.OnCountrySelected
import com.example.pocpay.feature.transaction.contract.AddTransactionContract.Interaction.OnFirstNameChange
import com.example.pocpay.feature.transaction.contract.AddTransactionContract.Interaction.OnLastNameChange
import com.example.pocpay.feature.transaction.contract.AddTransactionContract.Interaction.OnSendClick
import com.example.pocpay.feature.transaction.contract.AddTransactionContract.Interaction.OnTransferValueChange
import com.example.pocpay.feature.transaction.domain.enums.CountryEnum
import com.example.pocpay.feature.transaction.domain.interactor.CurrencyExchangeUseCase
import com.example.pocpay.feature.transaction.domain.interactor.Result
import com.example.pocpay.feature.transaction.domain.interactor.TransactionUseCase
import com.example.pocpay.feature.transaction.domain.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val currencyExchangeUseCase: CurrencyExchangeUseCase,
    private val transactionUseCase: TransactionUseCase,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {
    var firstName by mutableStateOf("")
        private set

    var lastName by mutableStateOf("")
        private set

    var transferValue by mutableStateOf("")
        private set

    var country by mutableStateOf(CountryEnum.BRAZIL)
        private set

    var exchangeValue by mutableStateOf("")
        private set

    var exchangeLoading by mutableStateOf(false)
        private set

    var isFormValid by mutableStateOf(false)
        private set

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(interaction: AddTransactionContract.Interaction) {
        when (interaction) {
            is OnFirstNameChange -> firstName = interaction.value
            is OnLastNameChange -> lastName = interaction.value
            is OnTransferValueChange -> onTransferValueChanged(interaction.value)
            is OnCountrySelected -> TODO()
            is OnBackClick -> sendUiEvent(UiEvent.PopBackStack)
            is OnSendClick -> sendTransaction()
        }
        validateForm()
    }

    private fun onTransferValueChanged(value: String) {
        transferValue = value
        if (value.isNotEmpty()) {
            fetchExchangeValueAlternate()
        } else {
            exchangeValue = ""
        }
    }

    suspend fun abc() {
        withContext(coroutineDispatcherProvider.io()) {
            println()
        }
    }

    private fun fetchExchangeValue() {
        viewModelScope.launch {
            currencyExchangeUseCase
                .getCurrencyExchangeByCode(transferValue.toDouble(), country.currencyCode)
                .onStart { exchangeLoading = true }
                .flowOn(Dispatchers.IO)
                .collect { result ->
                    exchangeLoading = false
                    exchangeValue = when (result) {
                        is Result.Success -> result.data.value.toString()
                        is Result.Failure -> result.error
                    }
                    validateForm()
                }
        }
    }

    private fun fetchExchangeValueAlternate() {
        Log.e("", "")
        viewModelScope.launch {
            Dispatchers.IO
            Long
            currencyExchangeUseCase
                .getCurrencyExchangeByCodeAlternateTwo(transferValue.toDouble(), country.currencyCode)
                .onStart { exchangeLoading = true }
                .flowOn(
                    Dispatchers.IO
                )
                .catch { ex ->
                    exchangeLoading = false
                    exchangeValue = ex.message ?: ""
                    validateForm()
                }
                .collect { result ->
                    exchangeLoading = false
                    exchangeValue = result.value.toString()
                    validateForm()
                }
        }
    }

    private fun validateForm() {
        isFormValid = firstName.trim().isNotEmpty()
                && lastName.trim().isNotEmpty()
                && transferValue.trim().isNotEmpty()
                && exchangeValue != "ERROR" && exchangeValue.isNotEmpty()
    }

    private fun sendTransaction() {
        viewModelScope.launch {
            transactionUseCase.saveTransaction(
                Transaction(
                    firstName = firstName,
                    lastName = lastName,
                    country = country,
                    originalValue = transferValue.toDouble(),
                    exchangeValue = exchangeValue.toDouble(),
                    date = Date()
                )
            )
            sendUiEvent(UiEvent.PopBackStack)
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
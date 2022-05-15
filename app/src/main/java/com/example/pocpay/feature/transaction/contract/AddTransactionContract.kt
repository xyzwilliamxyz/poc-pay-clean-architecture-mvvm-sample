package com.example.pocpay.feature.transaction.contract

interface AddTransactionContract {
    sealed class ExchangeUiState {
        data class Loaded(val value: Double): ExchangeUiState()
        object Loading: ExchangeUiState()
        data class Error(val message: String): ExchangeUiState()
    }

    sealed class Interaction {
        data class OnFirstNameChange(val value: String): Interaction()
        data class OnLastNameChange(val value: String): Interaction()
        data class OnTransferValueChange(val value: String): Interaction()
        data class OnCountrySelected(val value: String): Interaction()
        object OnSendClick: Interaction()
        object OnBackClick: Interaction()
    }
}
package com.example.pocpay.feature.transaction.contract

import com.example.pocpay.feature.transaction.domain.model.Transaction

interface TransactionDetailsContract {

    sealed class UiState {
        object Empty: UiState()
        data class Loaded(val value: Transaction): UiState()
        object Loading: UiState()
        data class Error(val message: String): UiState()
    }

    sealed class Interaction {
        object OnBackClick: Interaction()
    }
}
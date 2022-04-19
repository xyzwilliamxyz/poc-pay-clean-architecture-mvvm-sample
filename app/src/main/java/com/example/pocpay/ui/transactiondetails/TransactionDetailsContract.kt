package com.example.pocpay.ui.transactiondetails

import com.example.pocpay.domain.model.Transaction

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
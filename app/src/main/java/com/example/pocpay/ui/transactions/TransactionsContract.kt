package com.example.pocpay.ui.transactions

import com.example.pocpay.domain.model.Transaction

interface TransactionsContract {

    sealed class UiState {
        object Empty: UiState()
        object Loading: UiState()
        data class Loaded(val value: List<Transaction>): UiState()
        data class Error(val message: String): UiState()
    }

    sealed class Interaction {
        object OnAddClick: Interaction()
    }
}
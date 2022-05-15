package com.example.pocpay.feature.transaction.contract

import com.example.pocpay.feature.transaction.domain.model.Transaction

interface TransactionsContract {

    sealed class UiState {
        object Empty: UiState()
        object Loading: UiState()
        data class Loaded(val value: List<Transaction>): UiState()
        data class Error(val message: String): UiState()
    }

    sealed class Interaction {
        object OnAddClick: Interaction()
        data class OnTransactionClick(val transaction: Transaction): Interaction()
    }
}
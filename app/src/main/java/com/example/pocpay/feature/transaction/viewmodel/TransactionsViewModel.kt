package com.example.pocpay.feature.transaction.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocpay.di.CoroutineDispatcherProvider
import com.example.pocpay.feature.transaction.domain.interactor.TransactionUseCase
import com.example.pocpay.feature.transaction.router.TransactionRouter
import com.example.pocpay.core.ui.UiEvent
import com.example.pocpay.feature.transaction.contract.TransactionsContract
import com.example.pocpay.feature.transaction.contract.TransactionsContract.Interaction.OnAddClick
import com.example.pocpay.feature.transaction.contract.TransactionsContract.Interaction.OnTransactionClick
import com.example.pocpay.feature.transaction.contract.TransactionsContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val transactionUseCase: TransactionUseCase,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState = _uiState.asStateFlow()

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        fetchTransactions()
    }

    fun onEvent(interaction: TransactionsContract.Interaction) {
        when (interaction) {
            is OnAddClick -> {
                sendUiEvent(UiEvent.Navigate(TransactionRouter.ADD_TRANSACTION))
            }
            is OnTransactionClick -> {
                val transactionId = interaction.transaction.id
                sendUiEvent(UiEvent.Navigate(TransactionRouter.getTransactionDetailsRoute(transactionId)))
            }
        }
    }

    private fun fetchTransactions() {
        viewModelScope.launch {
            transactionUseCase.getTransactions()
                .onStart { _uiState.value = UiState.Loading }
                .flowOn(coroutineDispatcherProvider.io())
                .catch { _uiState.value = UiState.Error("ERROR") }
                .collect { transactions ->
                    _uiState.value = UiState.Loaded(transactions)
                }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
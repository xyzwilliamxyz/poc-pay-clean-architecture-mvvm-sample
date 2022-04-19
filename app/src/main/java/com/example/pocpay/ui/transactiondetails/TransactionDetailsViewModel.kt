package com.example.pocpay.ui.transactiondetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocpay.di.CoroutineDispatcherProvider
import com.example.pocpay.domain.interactor.TransactionUseCase
import com.example.pocpay.ui.common.UiEvent
import com.example.pocpay.ui.transactiondetails.TransactionDetailsContract.Interaction.OnBackClick
import com.example.pocpay.ui.transactiondetails.TransactionDetailsContract.UiState
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
class TransactionDetailsViewModel @Inject constructor(
    private val transactionUseCase: TransactionUseCase,
    savedStateHandle: SavedStateHandle,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState = _uiState.asStateFlow()

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val transactionId = savedStateHandle.get<Long>("transactionId")
        if (transactionId != null) {
            loadTransactions(transactionId)
        }
    }

    fun onEvent(interaction: TransactionDetailsContract.Interaction) {
        when (interaction) {
            OnBackClick -> sendUiEvent(UiEvent.PopBackStack)
        }
    }

    private fun loadTransactions(transactionId: Long) {
        viewModelScope.launch {
            transactionUseCase
                .getTransactionById(transactionId)
                .onStart {  }
                .catch {
                    _uiState.value = UiState.Error("Error")
                }
                .flowOn(coroutineDispatcherProvider.io())
                .collect { transaction ->
                    _uiState.value = UiState.Loaded(transaction)
                }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
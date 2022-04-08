package com.example.pocpay.domain.interactor

import com.example.pocpay.data.local.repository.TransactionRepository
import com.example.pocpay.domain.model.Transaction
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class TransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {

    suspend fun getTransactions(): Flow<List<Transaction>> {
        return transactionRepository.getTransactions()
    }

    suspend fun getTransactionById(): Flow<List<Transaction>> {
        return transactionRepository.getTransactions()
    }

    suspend fun saveTransaction(transaction: Transaction) {
        transactionRepository.saveTransaction(transaction)
    }
}
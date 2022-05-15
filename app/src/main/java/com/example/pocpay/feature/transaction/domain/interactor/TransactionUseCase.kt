package com.example.pocpay.feature.transaction.domain.interactor

import com.example.pocpay.data.local.repository.TransactionRepository
import com.example.pocpay.feature.transaction.domain.model.Transaction
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class TransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {

    suspend fun getTransactions(): Flow<List<Transaction>> {
        return transactionRepository.getTransactions()
    }

    suspend fun getTransactionById(transactionId: Long): Flow<Transaction> {
        return transactionRepository.getTransactionById(transactionId)
    }

    suspend fun saveTransaction(transaction: Transaction) {
        transactionRepository.saveTransaction(transaction)
    }
}
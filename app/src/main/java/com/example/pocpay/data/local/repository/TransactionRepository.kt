package com.example.pocpay.data.local.repository

import com.example.pocpay.data.local.dao.TransactionDao
import com.example.pocpay.data.local.mapper.TransactionMapper
import com.example.pocpay.domain.model.Transaction
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao,
    private val mapper: TransactionMapper
) {

    suspend fun saveTransaction(transaction: Transaction) {
        transactionDao.insert(mapper.toEntity(transaction))
    }

    suspend fun getTransactions(): Flow<List<Transaction>> {
        return transactionDao.getAll()
            .map { entities -> mapper.fromEntityList(entities) }
    }

    suspend fun getTransactionById(transactionId: Long): Flow<Transaction> {
        return transactionDao.getTransactionById(transactionId)
            .map { entity -> mapper.fromEntity(entity) }
    }
}
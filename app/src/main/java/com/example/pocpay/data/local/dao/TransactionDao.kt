package com.example.pocpay.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pocpay.data.local.entity.TransactionEntity
import com.example.pocpay.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert
    fun insert(transactionEntity: TransactionEntity)

    @Query("SELECT * FROM transactions")
    fun getAll(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE transaction_id = :transactionId")
    fun getTransactionById(transactionId: Long): Flow<TransactionEntity>
}
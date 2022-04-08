package com.example.pocpay.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pocpay.data.local.converter.DateConverter
import com.example.pocpay.data.local.dao.TransactionDao
import com.example.pocpay.data.local.entity.TransactionEntity

@Database(
    entities = [
        TransactionEntity::class
    ],
    exportSchema = false,
    version = 1
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao() : TransactionDao
}
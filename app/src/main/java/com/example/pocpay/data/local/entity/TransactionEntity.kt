package com.example.pocpay.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "transactions",
    indices = [Index(
        value = ["transaction_id"],
        unique = true
    )]
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transaction_id")
    var id : Long,
    @ColumnInfo(name = "first_name")
    var firstName: String,
    @ColumnInfo(name = "last_name")
    var lastName: String,
    @ColumnInfo(name = "country_code")
    var countryCode: String,
    @ColumnInfo(name = "original_value")
    var originalValue: Double,
    @ColumnInfo(name = "exchange_value")
    var exchangeValue: Double,
    @ColumnInfo(name = "date")
    var date: Date
)

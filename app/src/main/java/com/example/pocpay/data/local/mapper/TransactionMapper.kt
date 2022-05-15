package com.example.pocpay.data.local.mapper

import com.example.pocpay.data.local.entity.TransactionEntity
import com.example.pocpay.feature.transaction.domain.enums.CountryEnum
import com.example.pocpay.feature.transaction.domain.model.Transaction
import javax.inject.Inject

class TransactionMapper @Inject constructor() {

    fun toEntity(transaction: Transaction): TransactionEntity {
        return TransactionEntity(
            id = transaction.id,
            firstName = transaction.firstName,
            lastName = transaction.lastName,
            countryCode = transaction.country.currencyCode,
            originalValue = transaction.originalValue,
            exchangeValue = transaction.exchangeValue,
            date = transaction.date
        )
    }

    fun fromEntity(entity: TransactionEntity): Transaction {
        return Transaction(
            id = entity.id,
            firstName = entity.firstName,
            lastName = entity.lastName,
            country = CountryEnum.fromCurrencyCode(entity.countryCode),
            originalValue = entity.originalValue,
            exchangeValue = entity.exchangeValue,
            date = entity.date
        )
    }

    fun fromEntityList(entities: List<TransactionEntity>): List<Transaction> {
        return entities.map { entity -> fromEntity(entity) }
    }
}
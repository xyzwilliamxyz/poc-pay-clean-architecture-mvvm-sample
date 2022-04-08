package com.example.pocpay.domain.model

import com.example.pocpay.domain.enums.CountryEnum
import java.util.Date

data class Transaction(
    var id : Long = 0,
    var firstName: String = "",
    var lastName: String = "",
    var country: CountryEnum = CountryEnum.BRAZIL,
    var originalValue: Double = 0.0,
    var exchangeValue: Double = 0.0,
    var date: Date
)

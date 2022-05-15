package com.example.pocpay.feature.transaction.domain.enums

enum class CountryEnum(
    val currencyCode: String,
    val countryName: String,
    val flag: Int
) {
    BRAZIL("BRL", "Brazil", 0),
    IRELAND("EUR", "Ireland", 0),
    POLAND("PLN", "Poland", 0);

    companion object {
        private val map = values().associateBy(CountryEnum::currencyCode)

        fun fromCurrencyCode(currencyCode: String) = map[currencyCode] ?: BRAZIL
    }
}
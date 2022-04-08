package com.example.pocpay.router

object TransactionRouter {
    const val PARAM_TRANSACTION_ID = "transactionId"

    const val TRANSACTIONS = "transactions_screen"
    const val ADD_TRANSACTION = "add_transaction_screen"
    const val TRANSACTION_DETAILS = "transaction_details_screen/{$PARAM_TRANSACTION_ID}"
}
package com.example.pocpay.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.toUSDateTimeFormat(): String {
    return SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.US).format(this)
}
package com.example.pocpay.feature.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pocpay.feature.transaction.domain.model.Transaction
import com.example.pocpay.extensions.toUSDateTimeFormat
import com.example.pocpay.core.ui.theme.PocPayTheme
import java.util.*

@Composable
fun TransactionItem(
    modifier: Modifier = Modifier,
    transaction: Transaction
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(0.5f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Recipient: ${transaction.firstName}")
            Text(text = "Value: ${transaction.originalValue}")
        }
        Column(
            modifier = Modifier.weight(0.5f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            Text(text = "Date: ${transaction.date.toUSDateTimeFormat()}")
        }
    }
}

@Preview
@Composable
private fun Preview() {
    PocPayTheme {
        TransactionItem(
            transaction = Transaction(date = Date())
        )
    }
}
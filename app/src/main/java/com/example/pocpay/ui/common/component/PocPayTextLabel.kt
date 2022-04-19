package com.example.pocpay.ui.common.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pocpay.ui.theme.spacing

@Composable
fun PocPayTextLabel(
    modifier: Modifier = Modifier,
    label: String,
    value: String
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = MaterialTheme.spacing.small,
                horizontal = MaterialTheme.spacing.medium
            ),
        text = "$label: $value"
    )
}
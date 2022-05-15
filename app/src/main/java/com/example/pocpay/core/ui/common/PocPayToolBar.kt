package com.example.pocpay.core.ui.common

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.pocpay.R

@Composable
fun PocPayToolbar(
    modifier: Modifier = Modifier,
    title: String,
    backAvailable: Boolean = false,
    onBackClicked: () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(title)
        },
        navigationIcon = if (backAvailable) {
            {
                IconButton(onClick = { onBackClicked() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button_description),
                        tint = Color.White
                    )
                }
            }
        } else null
    )
}
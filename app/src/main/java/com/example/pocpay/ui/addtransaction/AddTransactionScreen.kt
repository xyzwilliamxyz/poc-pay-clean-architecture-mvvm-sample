package com.example.pocpay.ui.addtransaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pocpay.R
import com.example.pocpay.ui.addtransaction.AddTransactionContract.Interaction.OnBackClick
import com.example.pocpay.ui.addtransaction.AddTransactionContract.Interaction.OnFirstNameChange
import com.example.pocpay.ui.addtransaction.AddTransactionContract.Interaction.OnLastNameChange
import com.example.pocpay.ui.addtransaction.AddTransactionContract.Interaction.OnSendClick
import com.example.pocpay.ui.addtransaction.AddTransactionContract.Interaction.OnTransferValueChange
import com.example.pocpay.ui.common.UiEvent
import com.example.pocpay.ui.common.component.PocPayToolbar
import com.example.pocpay.ui.theme.PocPayTheme

@Composable
fun AddTransactionScreen(
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: AddTransactionViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> onNavigate()
                else -> Unit
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxWidth(),
        topBar = {
            PocPayToolbar(
                title = stringResource(R.string.title_add_transaction),
                backAvailable = true,
                onBackClicked = { viewModel.onEvent(OnBackClick) }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                value = viewModel.firstName,
                onValueChange = { viewModel.onEvent(OnFirstNameChange(it)) },
                label = { Text(text = "First Name") }
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                value = viewModel.lastName,
                onValueChange = { viewModel.onEvent(OnLastNameChange(it)) },
                label = { Text(text = "Last Name") }
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                value = viewModel.country.countryName,
                enabled = false,
                onValueChange = { /* TODO */ },
                label = { Text(text = "Recipient Value") }
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                value = viewModel.transferValue,
                onValueChange = { viewModel.onEvent(OnTransferValueChange(it)) },
                label = { Text(text = "Transfer Value") }
            )

            if (viewModel.exchangeLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                )
            } else {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    fontSize = 24.sp,
                    text = "Received value: ${viewModel.exchangeValue}"
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                onClick = { viewModel.onEvent(OnSendClick) },
                enabled = viewModel.isFormValid
            ) {
                Text(text = stringResource(R.string.send_button))
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    PocPayTheme {
        AddTransactionScreen(
            onNavigate = {}
        )
    }
}
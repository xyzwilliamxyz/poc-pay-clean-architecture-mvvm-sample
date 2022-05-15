package com.example.pocpay.feature.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.rememberScaffoldState
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
import com.example.pocpay.feature.transaction.contract.AddTransactionContract.Interaction.OnBackClick
import com.example.pocpay.feature.transaction.contract.AddTransactionContract.Interaction.OnFirstNameChange
import com.example.pocpay.feature.transaction.contract.AddTransactionContract.Interaction.OnLastNameChange
import com.example.pocpay.feature.transaction.contract.AddTransactionContract.Interaction.OnSendClick
import com.example.pocpay.feature.transaction.contract.AddTransactionContract.Interaction.OnTransferValueChange
import com.example.pocpay.core.ui.UiEvent
import com.example.pocpay.core.ui.common.PocPayToolbar
import com.example.pocpay.core.ui.theme.PocPayTheme
import com.example.pocpay.feature.transaction.viewmodel.AddTransactionViewModel

@Composable
fun AddTransactionScreen(
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: AddTransactionViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

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
        scaffoldState = scaffoldState,
        topBar = {
            PocPayToolbar(
                title = stringResource(R.string.title_add_transaction),
                backAvailable = true,
                onBackClicked = { viewModel.onEvent(OnBackClick) }
            )
        }
    ) {
        AddTransactionForm(viewModel = viewModel)
//        TestForm()
    }
}

@Composable
private fun TestForm() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        TextField(
            value = "a",
            onValueChange = {},
            modifier = Modifier.padding(vertical = 16.dp)
        )
        TextField(
            value = "b",
            onValueChange = {},
            modifier = Modifier.padding(vertical = 16.dp)
        )
        TextField(
            value = "c",
            onValueChange = {},
            modifier = Modifier.padding(vertical = 16.dp)
        )
        TextField(
            value = "d",
            onValueChange = {},
            modifier = Modifier.padding(vertical = 16.dp)
        )
        TextField(
            value = "e",
            onValueChange = {},
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Row {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Ok")
            }
        }
    }
}

@Composable
private fun AddTransactionForm(viewModel: AddTransactionViewModel) {
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

@Preview
@Composable
private fun LoginScreenPreview() {
    PocPayTheme {
        AddTransactionScreen(
            onNavigate = {}
        )
    }
}
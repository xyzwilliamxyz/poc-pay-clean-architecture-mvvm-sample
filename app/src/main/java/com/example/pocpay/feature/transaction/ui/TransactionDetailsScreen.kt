package com.example.pocpay.feature.transactiondetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pocpay.R
import com.example.pocpay.feature.transaction.domain.model.Transaction
import com.example.pocpay.core.ui.UiEvent
import com.example.pocpay.core.ui.common.PocPayTextLabel
import com.example.pocpay.core.ui.common.PocPayToolbar
import com.example.pocpay.core.ui.theme.spacing
import com.example.pocpay.feature.transaction.contract.TransactionDetailsContract
import com.example.pocpay.feature.transaction.contract.TransactionDetailsContract.Interaction
import com.example.pocpay.feature.transaction.viewmodel.TransactionDetailsViewModel

@Composable
fun TransactionDetailsScreen(
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: TransactionDetailsViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
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
                title = stringResource(R.string.title_transaction_details),
                backAvailable = true,
                onBackClicked = { viewModel.onEvent(Interaction.OnBackClick) }
            )
        }
    ) {
        when (uiState) {
            is TransactionDetailsContract.UiState.Empty -> {}
            is TransactionDetailsContract.UiState.Error -> {}
            is TransactionDetailsContract.UiState.Loaded -> TransactionDetails(transaction = uiState.value)
            is TransactionDetailsContract.UiState.Loading -> {}
        }
    }
}

@Composable
private fun TransactionDetails(
    modifier: Modifier = Modifier,
    transaction: Transaction
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PocPayTextLabel(
            label = "First Name",
            value = transaction.firstName
        )
        PocPayTextLabel(
            label = "Last Name",
            value = transaction.lastName
        )
        PocPayTextLabel(
            label = "Country",
            value = transaction.country.countryName
        )
        PocPayTextLabel(
            label = "Transfer Value",
            value = transaction.originalValue.toString()
        )

        Divider(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
            color = Color.LightGray
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = MaterialTheme.spacing.small, horizontal = MaterialTheme.spacing.medium),
            fontSize = 24.sp,
            text = "Received value: ${transaction.exchangeValue}"
        )
    }
}
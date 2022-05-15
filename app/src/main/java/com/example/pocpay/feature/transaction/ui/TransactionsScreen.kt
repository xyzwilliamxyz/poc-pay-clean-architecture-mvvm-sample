package com.example.pocpay.feature.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pocpay.R
import com.example.pocpay.feature.transaction.domain.model.Transaction
import com.example.pocpay.core.ui.UiEvent
import com.example.pocpay.core.ui.common.PocPayToolbar
import com.example.pocpay.feature.transaction.contract.TransactionsContract
import com.example.pocpay.feature.transaction.contract.TransactionsContract.Interaction
import com.example.pocpay.feature.transaction.contract.TransactionsContract.Interaction.OnTransactionClick
import com.example.pocpay.feature.transaction.viewmodel.TransactionsViewModel

@Composable
fun TransactionsScreen(
    modifier: Modifier = Modifier,
    viewModel: TransactionsViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit
) {
    val uiState = viewModel.uiState.collectAsState().value
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event.route)
                else -> Unit
            }
        }
    }

    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = {
            PocPayToolbar(
                title = stringResource(R.string.title_transactions)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(Interaction.OnAddClick)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_button)
                )
            }
        }
    ) {
        when (uiState) {
            is TransactionsContract.UiState.Empty -> {}
            is TransactionsContract.UiState.Loading -> {}
            is TransactionsContract.UiState.Error -> {}
            is TransactionsContract.UiState.Loaded -> TransactionsList(viewModel, uiState.value)
        }
    }
}

@Composable
private fun TransactionsList(
    viewModel: TransactionsViewModel,
    transactions: List<Transaction>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(transactions) { index, transaction ->
            TransactionItem(
                transaction = transaction,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.onEvent(OnTransactionClick(transaction)) }
                    .padding(16.dp)
            )
            if (index < transactions.lastIndex) {
                Divider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.LightGray
                )
            }
        }
    }
}
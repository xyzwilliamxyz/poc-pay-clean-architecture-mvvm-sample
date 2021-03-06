package com.example.pocpay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pocpay.feature.transaction.router.TransactionRouter
import com.example.pocpay.feature.transaction.router.TransactionRouter.PARAM_TRANSACTION_ID
import com.example.pocpay.feature.add.AddTransactionScreen
import com.example.pocpay.core.ui.theme.PocPayTheme
import com.example.pocpay.feature.transactiondetails.TransactionDetailsScreen
import com.example.pocpay.feature.list.TransactionsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PocPayTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = TransactionRouter.TRANSACTIONS
                ) {
                    composable(TransactionRouter.TRANSACTIONS) {
                        TransactionsScreen(
                            onNavigate = {
                                navController.navigate(it)
                            }
                        )
                    }

                    composable(TransactionRouter.ADD_TRANSACTION) {
                        AddTransactionScreen(
                            onNavigate = {
                                navController.popBackStack()
                            }
                        )
                    }

                    composable(
                        route = TransactionRouter.TRANSACTION_DETAILS,
                        arguments = listOf(
                            navArgument(name = PARAM_TRANSACTION_ID) {
                                type = NavType.LongType
                                defaultValue = -1L
                            }
                        )
                    ) {
                        TransactionDetailsScreen(
                            onNavigate = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}
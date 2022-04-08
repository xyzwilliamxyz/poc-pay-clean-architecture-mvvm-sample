package com.example.pocpay.di

import kotlinx.coroutines.Dispatchers

class CoroutineDispatcherProvider {

    fun io() = Dispatchers.IO

    fun default() = Dispatchers.Default

    fun main() = Dispatchers.Main
}
package com.example.pocpay.di

import android.content.Context
import androidx.room.Room
import com.example.pocpay.data.local.AppDatabase
import com.example.pocpay.data.local.dao.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context) : AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).allowMainThreadQueries().build()
    }

    @Provides
    fun provideTransactionDao(appDatabase: AppDatabase) : TransactionDao {
        return appDatabase.transactionDao()
    }

    private const val DB_NAME = "pocpaydb"
}
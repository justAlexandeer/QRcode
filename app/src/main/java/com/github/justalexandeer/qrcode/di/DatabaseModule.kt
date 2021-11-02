package com.github.justalexandeer.qrcode.di

import android.content.Context
import androidx.room.Room
import com.github.justalexandeer.qrcode.data.db.AppDatabase
import com.github.justalexandeer.qrcode.data.db.QRCodeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideQRCodeDao(database: AppDatabase): QRCodeDao {
        return database.qrCodeDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "AppDatabase"
        ).build()
    }
}
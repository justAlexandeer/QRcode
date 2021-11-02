package com.github.justalexandeer.qrcode.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.justalexandeer.qrcode.data.db.entity.QRCodeEntity

@Database(
    entities = [QRCodeEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun qrCodeDao(): QRCodeDao
}
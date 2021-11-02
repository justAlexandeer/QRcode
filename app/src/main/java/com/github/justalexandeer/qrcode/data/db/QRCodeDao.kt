package com.github.justalexandeer.qrcode.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.justalexandeer.qrcode.data.db.entity.QRCodeEntity

@Dao
interface QRCodeDao {

    @Insert
    suspend fun insertQRCode(qrCodeEntity: QRCodeEntity): Long

    @Query("SELECT * FROM QRCodeEntity")
    suspend fun getAllQRCode(): List<QRCodeEntity>
}
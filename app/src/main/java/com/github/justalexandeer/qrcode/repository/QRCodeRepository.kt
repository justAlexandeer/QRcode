package com.github.justalexandeer.qrcode.repository

import android.content.Context
import com.github.justalexandeer.qrcode.data.db.QRCodeDao
import com.github.justalexandeer.qrcode.data.db.entity.QRCodeEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QRCodeRepository @Inject constructor(
    private val qrCodeDao: QRCodeDao,
    @ApplicationContext val appContext: Context,
){

    suspend fun saveQRCodeToDataBase(qrCodeEntity: QRCodeEntity): Long {
        return qrCodeDao.insertQRCode(qrCodeEntity)
    }

    suspend fun getAllQRCodeFromDB(): List<QRCodeEntity> {
        return qrCodeDao.getAllQRCode()
    }

}
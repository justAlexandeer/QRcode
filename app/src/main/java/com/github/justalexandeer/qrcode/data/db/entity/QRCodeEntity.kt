package com.github.justalexandeer.qrcode.data.db.entity

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.justalexandeer.qrcode.data.model.TypeQRCode

@Entity
data class QRCodeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val content: String,
    val typeQRCode: TypeQRCode,
    val uri: String
)
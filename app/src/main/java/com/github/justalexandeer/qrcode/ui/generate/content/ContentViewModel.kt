package com.github.justalexandeer.qrcode.ui.generate.content

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Environment
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.justalexandeer.qrcode.data.db.entity.QRCodeEntity
import com.github.justalexandeer.qrcode.data.model.TypeQRCode
import com.github.justalexandeer.qrcode.repository.QRCodeRepository
import com.github.justalexandeer.qrcode.util.millisToDate
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class ContentViewModel @Inject constructor(
    var repository: QRCodeRepository,
    @ApplicationContext val context: Context
) : ViewModel() {

    val bitmapState = MutableStateFlow<Bitmap?>(null)
    val idQRCode = MutableStateFlow<Long?>(null)

    fun generateQRCode(content: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val writer = QRCodeWriter()
            val hints: MutableMap<EncodeHintType, Any> = mutableMapOf()
            hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.M
            hints[EncodeHintType.CHARACTER_SET] = "utf-8"
            val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512, hints)
            val bitmap =
                Bitmap.createBitmap(bitMatrix.width, bitMatrix.height, Bitmap.Config.RGB_565)
            for (x in 0 until bitMatrix.width) {
                for (y in 0 until bitMatrix.height) {
                    bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
                }
            }
            bitmapState.value = bitmap
        }
    }

    fun saveImageToExternalStorageAndDataBase(
        qrCodeBitmap: Bitmap,
        typeQRCode: TypeQRCode,
        content: String
    ) {
        val file = File(
            context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES
            ), "QRCode"
        )
        file.mkdir()
        val fileName = "QRCode_${typeQRCode}_${millisToDate(System.currentTimeMillis())}.jpg"
        val fileQRCode = File(file, fileName)
        val fileOutputStream = FileOutputStream(fileQRCode)
        fileOutputStream.use {
            qrCodeBitmap.compress(Bitmap.CompressFormat.JPEG, 95, it)
        }
        fileOutputStream.flush()

        val qrCodeEntity = QRCodeEntity(
            content = content,
            typeQRCode = typeQRCode,
            uri = fileQRCode.toUri().toString()
        )

        viewModelScope.launch {
            idQRCode.value = repository.saveQRCodeToDataBase(qrCodeEntity)
        }
    }

    companion object {
        private const val TAG = "ContentViewModel"
    }
}
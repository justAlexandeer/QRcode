package com.github.justalexandeer.qrcode.ui.scan

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import java.nio.ByteBuffer

class QRCodeAnalyzer : ImageAnalysis.Analyzer {

    private val listOfListener: MutableList<QRCodeAnalyzerListener> = mutableListOf()

    fun unRegister(listener: QRCodeAnalyzerListener) {
        listOfListener.remove(listener)
    }

    fun register(listener: QRCodeAnalyzerListener) {
        listOfListener.add(listener)
    }

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()
        val data = ByteArray(remaining())
        get(data)
        return data
    }

    override fun analyze(image: ImageProxy) {
        val reader = MultiFormatReader()
        val data = image.planes[0].buffer.toByteArray()
        image.close()

        val source = PlanarYUVLuminanceSource(
            data,
            image.width,
            image.height,
            0,
            0,
            image.width,
            image.height,
            false
        )

        val binaryBitmap = BinaryBitmap(HybridBinarizer(source))

        try {
            val resultQRCode = reader.decode(binaryBitmap)
            listOfListener.forEach {
                it.onDetect(resultQRCode)
            }
        } catch (e: NotFoundException) {

        }
    }
}
package com.github.justalexandeer.qrcode.ui.generate

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import com.github.justalexandeer.qrcode.R
import com.github.justalexandeer.qrcode.data.model.ChooserQRCode
import com.github.justalexandeer.qrcode.data.model.TypeQRCode
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class GenerateViewModel @Inject constructor(
    @ApplicationContext val appContext: Context
) : ViewModel() {

    val listOfChooserQRCode = MutableStateFlow(getAllChooserQRCode())

    private fun getAllChooserQRCode(): List<ChooserQRCode> {
        val listOfChooserQRCode = mutableListOf<ChooserQRCode>()
        TypeQRCode.values().forEach {
            val idDrawable = appContext.resources.getIdentifier(
                "outline_${it.toString().lowercase()}_black_48dp",
                "drawable",
                appContext.packageName
            )
            listOfChooserQRCode.add(
                ChooserQRCode(
                    it,
                    appContext.resources.getDrawable(idDrawable, appContext.theme)
                )
            )
        }
        return listOfChooserQRCode
    }
}
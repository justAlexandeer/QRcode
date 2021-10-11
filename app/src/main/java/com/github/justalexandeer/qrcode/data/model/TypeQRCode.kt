package com.github.justalexandeer.qrcode.data.model

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class TypeQRCode : Parcelable {
    WEB, TEL, TEXT, WIFI, EVENT, CONTACT, MAIL, SMS;

    fun getLabel(context: Context): String =
        context.resources.getString(
            context.resources.getIdentifier(
                this.name,
                "string",
                context.packageName
            )
        )
}
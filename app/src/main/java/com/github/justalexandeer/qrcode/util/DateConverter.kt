package com.github.justalexandeer.qrcode.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

fun millisToDate(millis: Long): String {
    val requiredDateFormat = SimpleDateFormat("dd.MM.yyyy")
    return requiredDateFormat.format(millis).toString()
}
package com.github.justalexandeer.qrcode.ui.history

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.justalexandeer.qrcode.R
import com.github.justalexandeer.qrcode.data.db.entity.QRCodeEntity

class QRCodeViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(qrCodeEntity: QRCodeEntity) {
        val image = view.findViewById<ImageView>(R.id.view_holder_image)
        val textViewContent = view.findViewById<TextView>(R.id.view_holder_content)
        textViewContent.text = qrCodeEntity.content
        Glide.with(view)
            .load(Uri.decode(qrCodeEntity.uri))
            .into(image)
    }

    companion object {
        fun create(viewGroup: ViewGroup): QRCodeViewHolder {
            return QRCodeViewHolder(
                LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.viewholder_qr_code, viewGroup, false)
            )
        }
    }
}
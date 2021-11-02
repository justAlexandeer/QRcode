package com.github.justalexandeer.qrcode.ui.history

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.github.justalexandeer.qrcode.data.db.entity.QRCodeEntity
import com.google.zxing.qrcode.encoder.QRCode


class QRCodeAdapter : ListAdapter<QRCodeEntity, QRCodeViewHolder>(QRCodeDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QRCodeViewHolder {
        return QRCodeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: QRCodeViewHolder, position: Int) {
        val qrCode = getItem(position)
        holder.bind(qrCode)
    }

}

class QRCodeDiffUtil : DiffUtil.ItemCallback<QRCodeEntity>() {
    override fun areItemsTheSame(oldItem: QRCodeEntity, newItem: QRCodeEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: QRCodeEntity, newItem: QRCodeEntity): Boolean {
        return oldItem == newItem
    }
}
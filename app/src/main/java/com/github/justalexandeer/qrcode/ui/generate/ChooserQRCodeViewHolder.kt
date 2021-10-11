package com.github.justalexandeer.qrcode.ui.generate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.github.justalexandeer.qrcode.data.model.ChooserQRCode
import com.github.justalexandeer.qrcode.databinding.ViewholderChooserQrCodeBinding
import com.github.justalexandeer.qrcode.ui.base.ChooserQRCodeListener

class ChooserQRCodeViewHolder(
    private val view: View,
    private val binding: ViewholderChooserQrCodeBinding,
    private val chooserQRCodeListener: ChooserQRCodeListener
) : RecyclerView.ViewHolder(view), View.OnClickListener {

    fun bind(chooserQRCode: ChooserQRCode) {
        binding.viewholderChooserImage.setImageDrawable(chooserQRCode.image)
        binding.viewholderChooserText.text = chooserQRCode.typeQRCode.getLabel(itemView.context)
        itemView.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        chooserQRCodeListener.onClick(adapterPosition)
    }

    companion object {
        fun create(
            parent: ViewGroup,
            chooserQRCodeListener: ChooserQRCodeListener
        ): ChooserQRCodeViewHolder {
            val binding = ViewholderChooserQrCodeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ChooserQRCodeViewHolder(binding.root, binding, chooserQRCodeListener)
        }
    }
}
package com.github.justalexandeer.qrcode.ui.generate

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.justalexandeer.qrcode.data.model.ChooserQRCode
import com.github.justalexandeer.qrcode.ui.base.ChooserQRCodeListener

class ChooserQRCodeAdapter(
    private val listChooserQRCode: List<ChooserQRCode>,
    private val chooserQRCodeListener: ChooserQRCodeListener
) :
    RecyclerView.Adapter<ChooserQRCodeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooserQRCodeViewHolder {
        return ChooserQRCodeViewHolder.create(parent, chooserQRCodeListener)
    }

    override fun onBindViewHolder(holder: ChooserQRCodeViewHolder, position: Int) {
        holder.bind(listChooserQRCode[position])
    }

    override fun getItemCount(): Int {
        return listChooserQRCode.size
    }
}
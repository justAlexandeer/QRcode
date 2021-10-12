package com.github.justalexandeer.qrcode.ui.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.github.justalexandeer.qrcode.databinding.FragmentQrCodeBinding

class QRCodeFragment: Fragment() {
    private lateinit var binding: FragmentQrCodeBinding
    private val args: QRCodeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQrCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.imageQrCode.setImageBitmap(args.qrCode)
    }

    companion object {
        private const val TAG = "QRCodeFragment"
    }
}
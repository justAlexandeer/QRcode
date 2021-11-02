package com.github.justalexandeer.qrcode.ui.base

import android.content.ContentValues
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.github.justalexandeer.qrcode.R
import com.github.justalexandeer.qrcode.databinding.FragmentQrCodeBinding
import com.github.justalexandeer.qrcode.util.millisToDate

class QRCodeFragment : Fragment() {
    private lateinit var binding: FragmentQrCodeBinding
    private val args: QRCodeFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQrCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.qr_code_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.qr_code_menu_download -> {
                saveQRCodeToGallery()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun saveQRCodeToGallery() {
        val resolver = requireContext().contentResolver
        val imageCollection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL_PRIMARY
                )
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }
        val newSongDetails = ContentValues().apply {
            put(
                MediaStore.Images.Media.DISPLAY_NAME,
                "QRCode_${args.typeQRCode}_${millisToDate(System.currentTimeMillis())}.jpg"
            )
        }
        resolver.insert(imageCollection, newSongDetails)?.let {
            resolver.openOutputStream(it).use { outputStream ->
                args.qrCode.compress(Bitmap.CompressFormat.JPEG, 95, outputStream)
            }
        }
        Toast.makeText(
            requireContext(),
            getString(R.string.qr_code_download_success),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.imageQrCode.setImageBitmap(args.qrCode)
    }

    companion object {
        private const val TAG = "QRCodeFragment"
    }
}
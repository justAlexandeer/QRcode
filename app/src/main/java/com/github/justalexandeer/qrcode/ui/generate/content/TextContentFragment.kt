package com.github.justalexandeer.qrcode.ui.generate.content

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.github.justalexandeer.qrcode.data.model.TypeQRCode
import com.github.justalexandeer.qrcode.databinding.FragmentTextContentBinding
import com.github.justalexandeer.qrcode.util.millisToDate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class TextContentFragment : Fragment() {
    private lateinit var binding: FragmentTextContentBinding
    private val viewModel: ContentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTextContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textContentText.addTextChangedListener {
            it?.let {
                binding.textContentButtonGenerate.isEnabled = it.toString().isNotEmpty()
            }
        }
        binding.textContentButtonGenerate.setOnClickListener {
            viewModel.generateQRCode(getContent())
        }
        lifecycleScope.launchWhenCreated {
            viewModel.bitmapState
                .collect {
                    it?.let {
                        viewModel.saveImageToExternalStorageAndDataBase(it, TypeQRCode.TEXT, getContent())
                    }
                }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.idQRCode
                .collect {
                    it?.let {
                        viewModel.bitmapState.value?.let { bitmap ->
                            val value = TextContentFragmentDirections.actionTextContentFragmentToQRCodeFragment(
                                bitmap, TypeQRCode.TEXT
                            )
                            navigateToQRCode(value)
                        }
                    }
                }
        }
    }

    private fun navigateToQRCode(navDirections: NavDirections) {
        findNavController().navigate(navDirections)
    }

    private fun getContent(): String {
        var content = "text:"
        val editTextContentText = binding.textContentText.text.toString()
        content = content.plus(editTextContentText)
        return content
    }

    companion object {
        private const val TAG = "TextContentFragment"
    }
}


package com.github.justalexandeer.qrcode.ui.generate.content

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.justalexandeer.qrcode.data.model.TypeQRCode
import com.github.justalexandeer.qrcode.databinding.FragmentTextContentBinding
import kotlinx.coroutines.flow.collect

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
                        val value =
                            TextContentFragmentDirections.actionTextContentFragmentToQRCodeFragment(
                                it, TypeQRCode.TEXT
                            )
                        findNavController().navigate(value)
                        viewModel.bitmapState.value = null
                    }
                }
        }
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


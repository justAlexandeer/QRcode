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
import com.github.justalexandeer.qrcode.databinding.FragmentWebContentBinding
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter

class WebContentFragment : Fragment() {
    private lateinit var binding: FragmentWebContentBinding
    private val viewModel: ContentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.webContentWebSite.addTextChangedListener {
            it?.let {
                binding.webContentButtonGenerate.isEnabled = it.toString().isNotEmpty()
            }
        }
        binding.webContentButtonGenerate.setOnClickListener {
            viewModel.generateQRCode(getContent())
        }
        lifecycleScope.launchWhenCreated {
            viewModel.bitmapState
                .collect {
                    it?.let {
                        val value = WebContentFragmentDirections.actionGlobalQRCodeFragment(it)
                        findNavController().navigate(value)
                        viewModel.bitmapState.value = null
                    }
                }
        }
    }

    private fun getContent(): String = binding.webContentWebSite.text.toString()

    companion object {
        private const val TAG = "WebContentFragment"
    }
}
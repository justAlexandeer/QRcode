package com.github.justalexandeer.qrcode.ui.generate

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.justalexandeer.qrcode.R
import com.github.justalexandeer.qrcode.data.model.ChooserQRCode
import com.github.justalexandeer.qrcode.data.model.TypeQRCode
import com.github.justalexandeer.qrcode.databinding.FragmentGenerateBinding
import com.github.justalexandeer.qrcode.ui.base.ChooserQRCodeListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class GenerateFragment : Fragment(), ChooserQRCodeListener {

    private lateinit var binding: FragmentGenerateBinding
    private val viewModel: GenerateViewModel by viewModels()
    private val listChooserQRCode = mutableListOf<ChooserQRCode>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGenerateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupUI()
        setupObserver()
    }

    override fun onClick(position: Int) {
        /*val action = GenerateFragmentDirections.actionGenerateFragmentToFillContentFragment(
                listChooserQRCode[position].typeQRCode
            )
        findNavController().navigate(action)*/
        navigate(listChooserQRCode[position].typeQRCode)
    }

    private fun setupUI() {
        binding.listChooserQrCode.layoutManager = GridLayoutManager(context, 2)
    }

    private fun setupObserver() {
        lifecycleScope.launchWhenCreated {
            viewModel.listOfChooserQRCode.collect {
                listChooserQRCode.addAll(it)
                binding.listChooserQrCode.adapter = ChooserQRCodeAdapter(it, this@GenerateFragment)
            }
        }
    }

    private fun navigate(typeQRCode: TypeQRCode) {
        when(typeQRCode) {
            TypeQRCode.WEB -> {
                findNavController().navigate(R.id.action_generateFragment_to_webContentFragment)
            }
            TypeQRCode.TEL -> TODO()
            TypeQRCode.TEXT -> {
                findNavController().navigate(R.id.action_generateFragment_to_textContentFragment)
            }
            TypeQRCode.WIFI -> TODO()
            TypeQRCode.EVENT -> TODO()
            TypeQRCode.CONTACT -> TODO()
            TypeQRCode.MAIL -> TODO()
            TypeQRCode.SMS -> TODO()
        }


    }

    companion object {
        private const val TAG = "GenerateFragment"
    }
}
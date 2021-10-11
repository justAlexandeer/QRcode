package com.github.justalexandeer.qrcode.ui.generate.content

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.github.justalexandeer.qrcode.databinding.FragmentFillContentBinding

class FillContentFragment: Fragment() {
    private lateinit var binding: FragmentFillContentBinding
    private val args: FillContentFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFillContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated: ${args.typeQRCode.name.toString()}")
    }


    companion object {
        private const val TAG = "FillContentFragment"
    }
}
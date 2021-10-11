package com.github.justalexandeer.qrcode.ui.generate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.justalexandeer.qrcode.databinding.FragmentGenerateBinding

class GenerateFragment: Fragment() {
    private lateinit var binding: FragmentGenerateBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGenerateBinding.inflate(inflater, container, false)
        return binding.root
    }

}
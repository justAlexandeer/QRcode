package com.github.justalexandeer.qrcode.ui.scan

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.BackpressureStrategy
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.github.justalexandeer.qrcode.R
import com.github.justalexandeer.qrcode.databinding.FragmentScanBinding
import com.google.zxing.Result
import java.nio.ByteBuffer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ScanFragment : Fragment(), QRCodeAnalyzerListener {
    private lateinit var binding: FragmentScanBinding
    private lateinit var cameraExecutor: ExecutorService
    private var mUiHandler = Handler(Looper.getMainLooper())
    private val qrCodeAnalyzer = QRCodeAnalyzer()
    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            startCamera()
        } else {
            Toast.makeText(
                requireContext(),
                requireContext().getString(R.string.scan_fragment_permission_denied),
                Toast.LENGTH_SHORT
            ).show()
            requestCameraPermissions()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cameraExecutor = Executors.newSingleThreadExecutor()
        if (cameraPermissionGranted()) {
            qrCodeAnalyzer.register(this)
            binding.fragmentScanPreviewView.post {
                startCamera()
            }
        } else {
            requestCameraPermissions()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        qrCodeAnalyzer.unRegister(this)
        cameraExecutor.shutdown()
    }

    private fun startCamera() {
        val rotation = binding.fragmentScanPreviewView.display.rotation

        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .setTargetRotation(rotation)
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .build()
                .apply {
                    setSurfaceProvider(binding.fragmentScanPreviewView.surfaceProvider)
                }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetRotation(rotation)
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalysis.setAnalyzer(
                cameraExecutor,
                qrCodeAnalyzer
            )
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, imageAnalysis, preview
                )
            } catch (exc: Exception) {
                Log.i(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    override fun onDetect(result: Result) {
        mUiHandler.post {
            Log.i(TAG, "onDetect: ${result.text}")
        }
    }

    private fun requestCameraPermissions() {
        activityResultLauncher.launch(Manifest.permission.CAMERA)
    }

    private fun cameraPermissionGranted() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

    companion object {
        private const val TAG = "ScanFragment"
    }
}

interface QRCodeAnalyzerListener {
    fun onDetect(result: Result)
}
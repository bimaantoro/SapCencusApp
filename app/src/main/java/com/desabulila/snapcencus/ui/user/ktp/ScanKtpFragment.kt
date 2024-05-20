package com.desabulila.snapcencus.ui.user.ktp

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.desabulila.snapcencus.R
import com.desabulila.snapcencus.databinding.FragmentScanKtpBinding
import com.desabulila.snapcencus.ui.user.ktp.camera.KtpCameraActivity
import com.desabulila.snapcencus.utils.REQUIRED_PERMISSION

class ScanKtpFragment : Fragment() {

    private var _binding: FragmentScanKtpBinding? = null
    private val binding get() = _binding!!

    private var currentImageUri: Uri? = null

    private fun allPermissionGranted() = ContextCompat.checkSelfPermission(
        requireActivity(),
        REQUIRED_PERMISSION
    ) == PackageManager.PERMISSION_GRANTED

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            showToast(getString(R.string.permission_granted))
        } else {
            showToast(getString(R.string.permission_denied))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentScanKtpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (!allPermissionGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        setupAction()
    }

    private fun setupAction() {
        binding.btnOpenCamera.setOnClickListener {
            startCamera()
        }

        binding.btnOpenGallery.setOnClickListener {
            startGallery()
        }
    }

    private fun startCamera() {
        val intent = Intent(requireActivity(), KtpCameraActivity::class.java)
        launcherIntentCamera.launch(intent)
    }


    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == KtpCameraActivity.CAMERA_RESULT) {
            currentImageUri = it.data?.getStringExtra(KtpCameraActivity.EXTRA_CAMERA_IMAGE)?.toUri()
            showImage()
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No Media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewIv.setImageURI(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
}
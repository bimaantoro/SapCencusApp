package com.desabulila.snapcencus.ui.user.ktp.scan

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.desabulila.snapcencus.R
import com.desabulila.snapcencus.databinding.ActivityScanKtpBinding
import com.desabulila.snapcencus.ui.user.ktp.KtpScanActivity
import com.desabulila.snapcencus.ui.user.ktp.camera.KtpCameraActivity
import com.desabulila.snapcencus.utils.REQUIRED_PERMISSION
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class ScanKtpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanKtpBinding
    private var currentImageUri: Uri? = null

    private fun allPermissionGranted() = ContextCompat.checkSelfPermission(
        this,
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityScanKtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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

        binding.btnUploadKtp.setOnClickListener {
            currentImageUri?.let {
                analyzeKtp(it)
            } ?: run {
                showToast(getString(R.string.empty_image_warning))
            }
        }
    }
    private fun startCamera() {
        val intent = Intent(this, KtpCameraActivity::class.java)
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

    private fun analyzeKtp(uri: Uri) {
        val textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val inputImage = InputImage.fromFilePath(this, uri)
        textRecognizer.process(inputImage)
            .addOnSuccessListener { visionText: Text ->
                // runTextRecognition(visionText)
            }

            .addOnFailureListener {
                showToast(it.message.toString())
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
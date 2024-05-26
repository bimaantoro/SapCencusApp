package com.desabulila.snapcencus.ui.user.ocr.ktp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
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
import com.desabulila.snapcencus.databinding.ActivityKtpOcrBinding
import com.desabulila.snapcencus.ui.user.ocr.camera.CameraActivity
import com.desabulila.snapcencus.ui.user.ocr.camera.CameraActivity.Companion.CAMERA_RESULT
import com.desabulila.snapcencus.ui.user.ocr.camera.CameraActivity.Companion.EXTRA_IMAGE_KTP

class KtpOcrActivity : AppCompatActivity() {

    private var currentImageUri: Uri? = null

    private val binding: ActivityKtpOcrBinding by lazy {
        ActivityKtpOcrBinding.inflate(layoutInflater)
    }

    private fun allPermissionGranted() = REQUIRED_PERMISSION.all {
        ContextCompat.checkSelfPermission(
            this.baseContext,
            it
        ) == PackageManager.PERMISSION_GRANTED

    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            showToast(getString(R.string.permission_denied_message))
            this.finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (!allPermissionGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION[0])
        }

        setupAction()
    }

    private fun setupAction() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnOpenCamera.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            launcherCamera.launch(intent)
        }

        binding.btnOpenGallery.setOnClickListener {
            if (isPhotoPickerAvailable()) {
                launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            } else {
                showToast(getString(R.string.photo_picker_error_message))
                openDocumentPicker()
            }
        }

        binding.btnUploadKtp.setOnClickListener {
            currentImageUri?.let {
            } ?: run {
                showToast(getString(R.string.empty_image_warning))
            }
        }
    }

    private fun isPhotoPickerAvailable(): Boolean {
        val photoPickerIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        val packageManager = packageManager
        return photoPickerIntent.resolveActivity(packageManager) != null
    }

    private fun openDocumentPicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        launcherOpenDocument.launch(intent)
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.ivPreviewKtp.setImageURI(it)
            binding.tvPreviewKtpLabel.visibility = View.GONE
        }
    }


    private val launcherCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_RESULT) {
            currentImageUri = it.data?.getStringExtra(EXTRA_IMAGE_KTP)?.toUri()
            showImage()
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No Media selected")
            showToast(getString(R.string.picker_error_message))
        }
    }

    private val launcherOpenDocument = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            val uri = data?.data
            if (uri != null) {
                currentImageUri = uri
                showImage()
            }
        } else {
            getString(R.string.picker_error_message)
            Log.d("Document Picker", getString(R.string.picker_error_message))
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "KtpScanActivity"
        private val REQUIRED_PERMISSION = arrayOf(Manifest.permission.CAMERA)
    }
}
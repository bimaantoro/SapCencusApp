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
import com.desabulila.snapcencus.data.model.KtpModel
import com.desabulila.snapcencus.databinding.ActivityKtpOcrBinding
import com.desabulila.snapcencus.ui.user.ocr.camera.CameraActivity
import com.desabulila.snapcencus.ui.user.ocr.camera.CameraActivity.Companion.CAMERA_RESULT
import com.desabulila.snapcencus.ui.user.ocr.camera.CameraActivity.Companion.EXTRA_IMAGE_KTP
import com.desabulila.snapcencus.ui.user.ocr.result.ResultKtpOcrActivity
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

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

        binding.btnOpenGallery.setOnClickListener {
            if (isPhotoPickerAvailable()) {
                launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            } else {
                showToast(getString(R.string.photo_picker_error_message))
                openDocumentPicker()
            }
        }

        binding.btnOpenCamera.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            launcherCamera.launch(intent)
        }

        binding.btnUploadKtp.setOnClickListener {
            currentImageUri?.let {
                analyzeImageKtp(it)
            } ?: run {
                showToast(getString(R.string.empty_image_warning_message))
            }
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

    private fun isPhotoPickerAvailable(): Boolean {
        val photoPickerIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        return photoPickerIntent.resolveActivity(packageManager) != null
    }

    private fun openDocumentPicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        launcherOpenDocument.launch(intent)
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
            Log.d("Document Picker", getString(R.string.picker_error_message))
            showToast(getString(R.string.picker_error_message))
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

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.ivPreviewKtp.setImageURI(it)
            binding.tvPreviewKtpLabel.visibility = View.GONE
        }
    }

    private fun analyzeImageKtp(uri: Uri) {
        binding.progressBar.visibility = View.VISIBLE

        val textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val inputImage: InputImage = InputImage.fromFilePath(this, uri)
        textRecognizer.process(inputImage)
            .addOnSuccessListener { visionText: Text ->
                val detectedText = visionText.text
                if (detectedText.isNotBlank()) {
                    Log.d("MAIN-detectedText", detectedText)
                    binding.progressBar.visibility = View.GONE

                    val ktpData = parseKtpData(detectedText)

                    val intent = Intent(this, ResultKtpOcrActivity::class.java)
                    intent.putExtra(ResultKtpOcrActivity.EXTRA_RESULT, ktpData)
                    startActivity(intent)

                    for (block in visionText.textBlocks) {
                        val blockText = block.text
                        Log.d("MAIN-block", blockText)
                        for (line in block.lines) {
                            val lineText = line.text
                            Log.d("MAIN-line", lineText)
                            for (element in line.elements) {
                                val elementText = element.text
                                Log.d("MAIN-element", elementText)
                            }
                        }
                    }

                } else {
                    binding.progressBar.visibility = View.GONE
                    showToast(getString(R.string.text_recognized_error_message))
                }

            }
            .addOnFailureListener {
                binding.progressBar.visibility = View.GONE
                showToast(it.message.toString())
            }
    }

    private fun parseKtpData(detectedText: String): KtpModel {
        var nik = ""
        val nama = ""
        val tempatLahir = ""
        val tanggalLahir = ""
        val jenisKelamin = ""
        val golDarah = ""
        val alamat = ""
        val rt = ""
        val rw = ""
        val kel = ""
        val kec = ""
        val agama = ""
        val statusKawin = ""
        val pekerjaan = ""
        val statusWargaNegara = ""

        when {
            detectedText.contains("NIK") -> nik = detectedText.substringAfter(":").trim()
        }

        return KtpModel(
            nik,
            nama,
            tempatLahir,
            tanggalLahir,
            jenisKelamin,
            golDarah,
            alamat,
            rt,
            rw,
            kel,
            kec,
            agama,
            statusKawin,
            pekerjaan,
            statusWargaNegara
        )
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "KtpScanActivity"
        private val REQUIRED_PERMISSION = arrayOf(Manifest.permission.CAMERA)
    }
}
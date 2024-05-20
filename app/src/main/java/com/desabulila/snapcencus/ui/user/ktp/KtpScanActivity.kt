package com.desabulila.snapcencus.ui.user.ktp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
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
import com.desabulila.snapcencus.data.model.KtpModel
import com.desabulila.snapcencus.databinding.ActivityKtpScanBinding
import com.desabulila.snapcencus.helper.saveKTP
import com.desabulila.snapcencus.ocr.GenerateKtpData
import com.desabulila.snapcencus.ui.user.ktp.camera.KtpCameraActivity
import com.desabulila.snapcencus.ui.user.ktp.camera.KtpCameraActivity.Companion.CAMERA_RESULT
import com.desabulila.snapcencus.ui.user.ktp.result.KtpResultActivity
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class KtpScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKtpScanBinding
    private var currentImageUri: Uri? = null
    private var ktpData: GenerateKtpData? = null
    private var finishScan = false
    private var nikRect: Rect? = null
    private var namaRect: Rect? = null
    private var tempatLahirRect: Rect? = null
    private var tanggalLahirRect: Rect? = null
    private var alamatRect: Rect? = null
    private var rtRect: Rect? = null
    private var rwRect: Rect? = null
    private var kelRect: Rect? = null
    private var kecamatanRect: Rect? = null

    private var resultScan: List<String>? = null


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
        binding = ActivityKtpScanBinding.inflate(layoutInflater)
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

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_RESULT) {
            currentImageUri = it.data?.getStringExtra(KtpCameraActivity.EXTRA_CAMERA_IMAGE)?.toUri()
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
                runTextRecognition(visionText)
            }

            .addOnFailureListener {
                showToast(it.message.toString())
            }
    }

    private fun runTextRecognition(detectedText: Text) {
        val results: MutableList<String> = mutableListOf()
        for (block in detectedText.textBlocks) {
            for (line in block.lines) {
                results.add(line.text)
            }
        }

        ktpData = GenerateKtpData(results, object : GenerateKtpData.Listener {
            override fun finishScan() {
                finishScan = true

                ktpData?.let {
                    startIntent(it)
                }
            }
        })

    }

    private fun startIntent(ktpData: GenerateKtpData) {
        val intent = Intent(this, KtpResultActivity::class.java).apply {
            putExtra("nik", ktpData.getNik()?.value)
            putExtra("nama", ktpData.getNama()?.value)
            putExtra("tempatLahir", ktpData.getTempatLahir()?.value)
            putExtra("tanggalLahir", ktpData.getTanggalLahir()?.value)
            putExtra("jenisKelamin", ktpData.getJenisKelamin()?.value)
            putExtra("alamat", ktpData.getAlamat()?.value)
            putExtra("rtRw", ktpData.getRtRw()?.value)
            putExtra("kelDesa", ktpData.getKelDesa()?.value)
            putExtra("kecamatan", ktpData.getKecamatan()?.value)
            putExtra("agama", ktpData.getAgama()?.value)
            putExtra("statusPerkawinan", ktpData.getStatusPerkawinan()?.value)
            putExtra("golonganDarah", ktpData.getGolonganDarah()?.value)
            putExtra("pekerjaan", ktpData.getPekerjaan()?.value)
            putExtra("kewarganegaraan", ktpData.getKewarganegaraan()?.value)

            val rtRw = ktpData.getRtRw().toString().split("/")
            rtRw.let {
                if (it.size > 1) {
                    putExtra("rw", it[1])
                    putExtra("rt", it[0])
                }
            }
        }

        val ktp = KtpModel().apply {
            nik = ktpData.getNik()?.value
            namaLengkap = ktpData.getNama()?.value
            jenisKelamin = ktpData.getJenisKelamin()?.value
            agama = ktpData.getAgama()?.value
            tempatLahir = ktpData.getTempatLahir()?.value
            tanggalLahir = ktpData.getTanggalLahir()?.value
            pekerjaan = ktpData.getPekerjaan()?.value
            statusWargaNegara = ktpData.getKewarganegaraan()?.value
            statusKawin = ktpData.getStatusPerkawinan()?.value
            golDarah = ktpData.getGolonganDarah()?.value
        }

        saveKTP(this@KtpScanActivity, ktp)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "KtpScanActivity"
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}
package com.desabulila.sapcencus.ui.user.ktp

import android.Manifest
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
import com.bumptech.glide.Glide
import com.desabulila.sapcencus.R
import com.desabulila.sapcencus.data.model.KtpModel
import com.desabulila.sapcencus.databinding.ActivityKtpScanBinding
import com.desabulila.sapcencus.helper.saveKTP
import com.desabulila.sapcencus.ocr.GenerateKtpData
import com.desabulila.sapcencus.ui.user.ktp.KtpCameraActivity.Companion.CAMERA_RESULT
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class KtpScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKtpScanBinding
    private var currentImageUri: Uri? = null
    lateinit var ktpData: GenerateKtpData
    private var finishScan = false

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
        val results: MutableList<String> = ArrayList()
        for (block in detectedText.textBlocks) {
            for (line in block.lines) {
                results.add(line.text)
            }
        }

        ktpData = GenerateKtpData(results, object : GenerateKtpData.Listener {
            override fun finishScan() {
                finishScan = true
            }
        })

        if (ktpData != null) {
            startIntent()
        }

    }

    private fun startIntent() {
        val intent = Intent(this, KtpResultActivity::class.java)
        intent.putExtra("nik", ktpData.getNik()?.value)
        intent.putExtra("nama", ktpData.getNama()?.value)
        intent.putExtra("tempatLahir", ktpData.getTempatLahir()?.value)
        intent.putExtra("tanggalLahir", ktpData.getTanggalLahir()?.value)
        intent.putExtra("jenisKelamin", ktpData.getJenisKelamin()?.value)
        intent.putExtra("alamat", ktpData.getAlamat()?.value)
        intent.putExtra("rtRw", ktpData.getRtRw()?.value)
        intent.putExtra("kelDesa", ktpData.getKelDesa()?.value)
        intent.putExtra("kecamatan", ktpData.getKecamatan()?.value)
        intent.putExtra("agama", ktpData.getAgama()?.value)
        intent.putExtra("statusPerkawinan", ktpData.getStatusPerkawinan()?.value)
        intent.putExtra("golonganDarah", ktpData.getGolonganDarah()?.value)
        intent.putExtra("pekerjaan", ktpData.getPekerjaan()?.value)
        intent.putExtra("kewarganegaraan", ktpData.getKewarganegaraan()?.value)
        val ktp = KtpModel()
        ktp.nik = ktpData.getNik()?.value
        ktp.nama_lengkap = ktpData.getNama()?.value
        ktp.jenis_kelamin = ktpData.getJenisKelamin()?.value
        ktp.agama = ktpData.getAgama()?.value
        ktp.tempat_lahir = ktpData.getTempatLahir()?.value
        ktp.tanggal_lahir = ktpData.getTanggalLahir()?.value
        ktp.pekerjaan = ktpData.getPekerjaan()?.value
        ktp.status_wni = ktpData.getKewarganegaraan()?.value
        val rtRw = ktpData.getRtRw().toString().split("/")
        if (rtRw.size > 1) {
            ktp.rw = rtRw[1]
            ktp.rt = rtRw[0]
        }
        ktp.status_kawin = ktpData.getStatusPerkawinan()?.value
        ktp.goldar = ktpData.getGolonganDarah()?.value
        saveKTP(this, ktp)
        startActivity(intent)
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


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}
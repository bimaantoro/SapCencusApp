package com.desabulila.snapcencus.ui.user.ocr.result

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.desabulila.snapcencus.R
import com.desabulila.snapcencus.databinding.ActivityResultKtpOcrBinding

class ResultKtpOcrActivity : AppCompatActivity() {


    private val binding: ActivityResultKtpOcrBinding by lazy {
        ActivityResultKtpOcrBinding.inflate(layoutInflater)
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

        setupAction()
        setupData()
    }

    private fun setupData() {
        val nik = intent.getStringExtra("nik")
        val nama = intent.getStringExtra("nama")
        val tempatLahir = intent.getStringExtra("tempatLahir")
        val tanggalLahir = intent.getStringExtra("tanggalLahir")
        val jenisKelamin = intent.getStringExtra("jenisKelamin")
        val golonganDarah = intent.getStringExtra("golonganDarah")
        val alamat = intent.getStringExtra("alamat")
        val rtRw = intent.getStringExtra("rtRw")
        val kelurahan = intent.getStringExtra("kel")
        val kecamatan = intent.getStringExtra("kec")
        val agama = intent.getStringExtra("agama")
        val statusPerkawinan = intent.getStringExtra("statusPerkawinan")
        val pekerjaan = intent.getStringExtra("pekerjaan")
        val statusKewarnegaraan = intent.getStringExtra("kewarganegaraan")

        binding.contentResultKtpOcr.contentFieldKtpOcr.apply {
            nikEdt.setText(nik)
            namaEdt.setText(nama)
            tempatLahirEdt.setText(tempatLahir)
            tglLahirResult.text = tanggalLahir
            jenisKelaminEdt.setText(jenisKelamin)
            edtGolDarah.setText(golonganDarah)
            edtAlamat.setText(alamat)
            edtRt.setText(rtRw)
            edtRw.setText(rtRw)
            edtKel.setText(kelurahan)
            edtKec.setText(kecamatan)
            edtAgama.setText(agama)
            edtStatusKawin.setText(statusPerkawinan)
            edtPekerjaan.setText(pekerjaan)
            edtKewarganegaraan.setText(statusKewarnegaraan)
        }
    }

    private fun setupAction() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }


    companion object {
        const val EXTRA_RESULT = "extra_result"
    }
}
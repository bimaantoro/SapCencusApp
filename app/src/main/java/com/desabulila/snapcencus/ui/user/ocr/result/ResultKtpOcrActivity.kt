package com.desabulila.snapcencus.ui.user.ocr.result

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.desabulila.snapcencus.R
import com.desabulila.snapcencus.data.model.KtpModel
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

    private fun setupAction() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupData() {
        val ktpData = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_RESULT, KtpModel::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_RESULT)
        }

        binding.contentResultKtpOcr.contentFieldKtpOcr.apply {
            nikEdt.setText(ktpData?.nik ?: "")
            namaEdt.setText(ktpData?.nama ?: "")
            tempatLahirEdt.setText(ktpData?.tempatLahir ?: "")
            tglLahirResult.text = ktpData?.tanggalLahir ?: ""
            jenisKelaminEdt.setText(ktpData?.jenisKelamin ?: "")
            edtAlamat.setText(ktpData?.alamat ?: "")
            edtRt.setText(ktpData?.rt ?: "")
            edtRw.setText(ktpData?.rw ?: "")
            edtKel.setText(ktpData?.kel ?: "")
            edtKec.setText(ktpData?.kec ?: "")
            edtAgama.setText(ktpData?.agama ?: "")
            edtStatusKawin.setText(ktpData?.statusKawin ?: "")
            edtPekerjaan.setText(ktpData?.pekerjaan ?: "")
            edtKewarganegaraan.setText(ktpData?.statusWargaNegara ?: "")
        }


    }


    companion object {
        const val EXTRA_RESULT = "extra_result"
    }
}
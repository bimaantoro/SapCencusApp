package com.desabulila.sapcencus.ui.user.ktp

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.desabulila.sapcencus.R
import com.desabulila.sapcencus.databinding.ActivityKtpResultBinding
import com.desabulila.sapcencus.helper.getKTP
import com.desabulila.sapcencus.utils.DatePickerFragment
import java.text.SimpleDateFormat
import java.util.*

class KtpResultActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener {

    private lateinit var binding: ActivityKtpResultBinding

    var sKtp = ""
    var sNama = ""

    //var sTtl = ""
    var sTempatLahir = ""
    var sTanggalLahir = ""
    var sAlamat = ""
    var sRtRw = ""
    var sKelurahan = ""
    var sKecamatan = ""
    var sAgama = ""
    var sJk = ""
    var sStatus = ""
    var sPekerjaan = ""
    var sWni = ""
    var golDar = ""

    var qKtp = ""
    var qNama = ""
    var qTempat = ""
    var qTanggal = ""
    var qAlamat = ""
    var qRt = ""
    var qRw = ""
    var qKelurahan = ""
    var qKecamatan = ""
    var qAgama = ""
    var qStatus = ""
    var qPekerjaan = ""
    var qJk = ""
    var qWni = ""
    var qGolDar = ""
    var isCamera = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityKtpResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupData()
        setupAction()
    }

    private fun setupAction() {
        binding.lytKtpResult.btnTanggalLahir.setOnClickListener {
            showDatePicker(it)
        }
    }

    private fun setupData() {
        sKtp = intent.getStringExtra("nik").toString()
        sNama = intent.getStringExtra("nama").toString()
        sTempatLahir = intent.getStringExtra("tempatLahir").toString()
        sTanggalLahir = intent.getStringExtra("tanggalLahir").toString()
        sAlamat = intent.getStringExtra("alamat").toString()
        sRtRw = intent.getStringExtra("rtRw").toString()
        sKelurahan = intent.getStringExtra("kelurahan").toString()
        sKecamatan = intent.getStringExtra("kecamatan").toString()
        sAgama = intent.getStringExtra("agama").toString()
        sJk = intent.getStringExtra("jenisKelamin").toString()
        sStatus = intent.getStringExtra("statusPerkawinan").toString()
        val ktp = getKTP(this)
        sKtp = ktp.nik.toString()
        sNama = ktp.nama_lengkap.toString()
        sJk = ktp.jenis_kelamin.toString()
        sAgama = ktp.agama.toString()
        sTempatLahir = ktp.tempat_lahir.toString()
        sTanggalLahir = ktp.tanggal_lahir.toString()
        sPekerjaan = ktp.pekerjaan.toString()
        sWni = ktp.status_wni.toString()
        sRtRw = ktp.rt.toString() + "/" + ktp.rw.toString()
        sStatus = ktp.status_kawin.toString()
        golDar = ktp.goldar.toString()


        binding.lytKtpResult.apply {
            nikEdt.setText(sKtp)
            namaEdt.setText(sNama)
            tempatLahirEdt.setText(sTempatLahir)
            tvTanggalLahir.text = sTanggalLahir
            alamatEdt.setText(sAlamat)
            kecEdt.setText(sKecamatan)
        }

    }

    fun showDatePicker(view: View) {
        val dialogFragment = DatePickerFragment()
        dialogFragment.show(supportFragmentManager, "datePicker")
    }


    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        binding.lytKtpResult.tvTanggalLahir.text = dateFormat.format(calendar.time)
    }
}
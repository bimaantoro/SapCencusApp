package com.desabulila.snapcencus.ui.user.ktp.result

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.desabulila.snapcencus.R
import com.desabulila.snapcencus.data.model.KtpModel
import com.desabulila.snapcencus.databinding.ActivityKtpResultBinding
import com.desabulila.snapcencus.helper.getKTP
import com.desabulila.snapcencus.utils.DatePickerFragment
import com.desabulila.snapcencus.utils.TANGGAL_LAHIR_DATE_PICKER
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class KtpResultActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener {

    private lateinit var binding: ActivityKtpResultBinding
    private lateinit var ktp: KtpModel

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
            showDatePicker(TANGGAL_LAHIR_DATE_PICKER)
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
        sNama = ktp.namaLengkap.toString()
        sJk = ktp.jenisKelamin.toString()
        sAgama = ktp.agama.toString()
        sTempatLahir = ktp.tempatLahir.toString()
        sTanggalLahir = ktp.tanggalLahir.toString()
        sPekerjaan = ktp.pekerjaan.toString()
        sWni = ktp.statusWargaNegara.toString()
        sRtRw = ktp.rt.toString() + "/" + ktp.rw.toString()
        sStatus = ktp.statusKawin.toString()
        golDar = ktp.golDarah.toString()


        binding.lytKtpResult.apply {
            nikEdt.setText(sKtp ?: "")
            namaEdt.setText(sNama ?: "")
            tempatLahirEdt.setText(sTempatLahir ?: "")
            tglLahirResult.text = sTanggalLahir ?: ""
            alamatEdt.setText(sAlamat ?: "")
            kecEdt.setText(sKecamatan ?: "")
        }

    }

    private fun showDatePicker(tag: String?) {
        val dialogFragment = DatePickerFragment()
        dialogFragment.show(supportFragmentManager, tag)
    }


    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        when (tag) {
            TANGGAL_LAHIR_DATE_PICKER -> {
                binding.lytKtpResult.tglLahirResult.text = dateFormat.format(calendar.time)
            }
        }
    }

    companion object {
        const val EXTRA_KTP = "extra_ktp"
    }
}
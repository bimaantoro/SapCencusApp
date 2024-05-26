package com.desabulila.snapcencus.ui.user.ocr.ktp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.desabulila.snapcencus.R
import com.desabulila.snapcencus.data.ResultState
import com.desabulila.snapcencus.databinding.ActivityResultKtpBinding
import com.desabulila.snapcencus.helper.getKTP
import com.desabulila.snapcencus.ui.ViewModelFactory
import com.desabulila.snapcencus.ui.user.ocr.ktp.viewmodel.ResultKtpViewModel
import com.desabulila.snapcencus.utils.DatePickerFragment
import com.desabulila.snapcencus.utils.EXTRA_AGAMA
import com.desabulila.snapcencus.utils.EXTRA_ALAMAT
import com.desabulila.snapcencus.utils.EXTRA_JENIS_KELAMIN
import com.desabulila.snapcencus.utils.EXTRA_KECAMATAN
import com.desabulila.snapcencus.utils.EXTRA_KELURAHAN
import com.desabulila.snapcencus.utils.EXTRA_NAMA
import com.desabulila.snapcencus.utils.EXTRA_NIK
import com.desabulila.snapcencus.utils.EXTRA_RT_RW
import com.desabulila.snapcencus.utils.EXTRA_STATUS_PERKAWINAN
import com.desabulila.snapcencus.utils.EXTRA_TANGGAL_LAHIR
import com.desabulila.snapcencus.utils.EXTRA_TEMPAT_LAHIR
import com.desabulila.snapcencus.utils.TANGGAL_LAHIR_DATE_PICKER
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ResultKtpActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener {

    private var nik = ""
    private var nama = ""
    private var tempatLahir = ""
    private var tanggalLahir = ""
    private var alamat = ""
    private var jenisKelamin = ""
    private var agama = ""
    private var pekerjaan = ""
    private var kelurahan = ""
    private var kecamatan = ""
    private var statusWargaNegara = ""
    private var rtRw = ""
    private var statusKawin = ""
    private var golonganDarah = ""


    private val binding: ActivityResultKtpBinding by lazy {
        ActivityResultKtpBinding.inflate(layoutInflater)
    }


    private val viewModel by viewModels<ResultKtpViewModel> {
        ViewModelFactory.getInstance(this)
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

        setupData()
        setupAction()
        fetchDataPenduduk()
    }

    private fun fetchDataPenduduk() {
        viewModel.listResult.observe(this) { resultState ->
            when (resultState) {
                is ResultState.Success -> {
                    val jenisKelaminList = resultState.data.jenisKelamin
                    val namaJenisKelaminList = jenisKelaminList.map { it.nama }
                    val idJenisKelaminList = jenisKelaminList.map { it.id }

                    val agamaList = resultState.data.agama
                    val namaAgamaList = agamaList.map { it.nama }
                    val idAgamaList = agamaList.map { it.id }

                    val statusKawinList = resultState.data.statusKawin
                    val namaStatusKawinList = statusKawinList.map { it.nama }
                    val idStatusKawinList = statusKawinList.map { it.id }

                    val pekerjaanList = resultState.data.pekerjaan
                    val namaPekerjaanList = pekerjaanList.map { it.nama }
                    val idPekerjaanList = pekerjaanList.map { it.id }

                    val dusunList = resultState.data.dusun
                    val namaDusunList =
                        dusunList.map { it.rt + " " + it.rw + " " + it.dusun }
                    val idDusunList = dusunList.map { it.id }


                }

                is ResultState.Error -> {}
                is ResultState.Loading -> {}
            }

        }
    }

    private fun setupAction() {
        binding.contentResultKtp.lytKtpResult.btnTanggalLahir.setOnClickListener {
            showDatePicker(TANGGAL_LAHIR_DATE_PICKER)
        }
    }

    private fun showDatePicker(tag: String?) {
        val dialogFragment = DatePickerFragment()
        dialogFragment.show(supportFragmentManager, tag)
    }

    private fun setupData() {
        nik = intent.getStringExtra(EXTRA_NIK).toString()
        nama = intent.getStringExtra(EXTRA_NAMA).toString()
        tempatLahir = intent.getStringExtra(EXTRA_TEMPAT_LAHIR).toString()
        tanggalLahir = intent.getStringExtra(EXTRA_TANGGAL_LAHIR).toString()
        alamat = intent.getStringExtra(EXTRA_ALAMAT).toString()
        rtRw = intent.getStringExtra(EXTRA_RT_RW).toString()
        kelurahan = intent.getStringExtra(EXTRA_KELURAHAN).toString()
        kecamatan = intent.getStringExtra(EXTRA_KECAMATAN).toString()
        agama = intent.getStringExtra(EXTRA_AGAMA).toString()
        jenisKelamin = intent.getStringExtra(EXTRA_JENIS_KELAMIN).toString()
        statusKawin = intent.getStringExtra(EXTRA_STATUS_PERKAWINAN).toString()

        val ktp = getKTP(this)
        nik = ktp.nik.toString()
        nama = ktp.namaLengkap.toString()
        jenisKelamin = ktp.jenisKelamin.toString()
        agama = ktp.agama.toString()
        tempatLahir = ktp.tempatLahir.toString()
        tanggalLahir = ktp.tanggalLahir.toString()
        pekerjaan = ktp.pekerjaan.toString()
        statusWargaNegara = ktp.statusWargaNegara.toString()
        rtRw = ktp.rt.toString() + "/" + ktp.rw.toString()
        statusKawin = ktp.statusKawin.toString()
        golonganDarah = ktp.golDarah.toString()

        binding.contentResultKtp.lytKtpResult.nikEdt.setText(nik)
        binding.contentResultKtp.lytKtpResult.namaEdt.setText(nama)
        binding.contentResultKtp.lytKtpResult.tempatLahirEdt.setText(tempatLahir)
        binding.contentResultKtp.lytKtpResult.tglLahirResult.text = tanggalLahir
        binding.contentResultKtp.lytKtpResult.alamatEdt.setText(alamat)
        binding.contentResultKtp.lytKtpResult.kecEdt.setText(kecamatan)
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        when (tag) {
            TANGGAL_LAHIR_DATE_PICKER -> {
                binding.contentResultKtp.lytKtpResult.tglLahirResult.text =
                    dateFormat.format(calendar.time)
            }
        }
    }
}
package com.desabulila.snapcencus.ui.user.ktp.result

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.desabulila.snapcencus.data.Result
import com.desabulila.snapcencus.databinding.ActivityResultScanKtpBinding
import com.desabulila.snapcencus.helper.getKTP
import com.desabulila.snapcencus.ui.ViewModelFactory
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ResultScanKtpActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener {

    private lateinit var binding: ActivityResultScanKtpBinding

    private val viewModel by viewModels<ResultKtpViewModel> {
        ViewModelFactory.getInstance(this)
    }

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResultScanKtpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupData()
        setupAction()
        fetchDataPenduduk()
    }

    private fun fetchDataPenduduk() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.result.collectLatest { result ->
                    when (result) {
                        is Result.Success -> {
                            val jenisKelaminList = result.data?.jenisKelamin
                            val namaJenisKelaminList = jenisKelaminList?.map { it.nama } ?: listOf()
                            val idJenisKelaminList = jenisKelaminList?.map { it.id } ?: listOf()

                            val agamaList = result.data?.agama
                            val namaAgamaList = agamaList?.map { it.nama } ?: listOf()
                            val idAgamaList = agamaList?.map { it.id } ?: listOf()

                            val statusKawinList = result.data?.statusKawin
                            val namaStatusKawinList = statusKawinList?.map { it.nama } ?: listOf()
                            val idStatusKawinList = statusKawinList?.map { it.id } ?: listOf()

                            val pekerjaanList = result.data?.pekerjaan
                            val namaPekerjaanList = pekerjaanList?.map { it.nama } ?: listOf()
                            val idPekerjaanList = pekerjaanList?.map { it.id } ?: listOf()

                            val dusunList = result.data?.dusun
                            val namaDusunList =
                                dusunList?.map { it.rt + " " + it.rw + " " + it.dusun } ?: listOf()
                            val idDusunList = dusunList?.map { it.id } ?: listOf(   )


                        }

                        is Result.Error -> {}
                        is Result.Loading -> {}
                    }

                }
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
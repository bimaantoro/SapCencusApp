package com.desabulila.snapcencus.ui.user.detail

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.desabulila.snapcencus.R
import com.desabulila.snapcencus.data.ResultState
import com.desabulila.snapcencus.databinding.ActivityDetailPendudukBinding
import com.desabulila.snapcencus.ui.ViewModelFactory
import com.desabulila.snapcencus.ui.user.main.MainUserActivity
import com.desabulila.snapcencus.utils.DatePickerFragment
import com.desabulila.snapcencus.utils.EXP_PASPOR_DATE_PICKER
import com.desabulila.snapcencus.utils.EXTRA_RESULT_NIK
import com.desabulila.snapcencus.utils.PENERBITAN_KTP_DATE_PICKER
import com.desabulila.snapcencus.utils.TANGGAL_CERAI_DATE_PICKER
import com.desabulila.snapcencus.utils.TANGGAL_KAWIN_DATE_PICKER
import com.desabulila.snapcencus.utils.TANGGAL_LAHIR_DATE_PICKER
import com.desabulila.snapcencus.utils.TimePickerFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DetailPendudukActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener,
    TimePickerFragment.DialogTimeListener {

    private val binding: ActivityDetailPendudukBinding by lazy {
        ActivityDetailPendudukBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<DetailPendudukViewModel> {
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

        setupAction()
        setupViewSpinner()
        setupDetailDataPenduduk()
        setupUpdatePenduduk()

    }

    private fun setupUpdatePenduduk() {
        binding.contentEditData.btnSave.setOnClickListener {
            val nik = binding.contentEditData.lyContentDataDiri.edtNik.text.toString().trim()
            val nama = binding.contentEditData.lyContentDataDiri.edtNama.text.toString()
            val statusKtpEl =
                binding.contentEditData.lyContentDataDiri.spinIdentitasEl.selectedItemPosition
            val statusRekam =
                binding.contentEditData.lyContentDataDiri.spinStatusRekam.selectedItemPosition
            val tagIdCard =
                binding.contentEditData.lyContentDataDiri.edtTagIdCard.text.toString().trim()
            val tempatCetakKtp =
                binding.contentEditData.lyContentDataDiri.edtTmptPenerbitanKtp.text.toString()
            val tanggalCetakKtp =
                binding.contentEditData.lyContentDataDiri.tglPenerbitanKtpResult.text.toString()
            val noKkSebelumnya =
                binding.contentEditData.lyContentDataDiri.edtNoKKSblm.text.toString().trim()
            val statusHubKeluarga =
                binding.contentEditData.lyContentDataDiri.spinHubKeluarga.selectedItemPosition
            val jenisKelamin =
                binding.contentEditData.lyContentDataDiri.spinJenisKelamin.selectedItemPosition
            val agama = binding.contentEditData.lyContentDataDiri.spinAgama.selectedItemPosition
            val statusPenduduk =
                binding.contentEditData.lyContentDataDiri.spinStatusPenduduk.selectedItemPosition


            val noaktaLahir =
                binding.contentEditData.lyContentDataLahir.edtNoAkta.text.toString().trim()
            val tempatLahir =
                binding.contentEditData.lyContentDataLahir.edtTempatLahir.text.toString()
            val tanggalLahir =
                binding.contentEditData.lyContentDataLahir.tglLahirResult.text.toString()
            val waktuLahir =
                binding.contentEditData.lyContentDataLahir.waktuLahirResult.text.toString()
            val tempatDilahirkan =
                binding.contentEditData.lyContentDataLahir.spinTempatDilahirkan.selectedItemPosition
            val jeniKelahiran =
                binding.contentEditData.lyContentDataLahir.spinJenisKelahiran.selectedItemPosition
            val kelahiranAnakKe =
                binding.contentEditData.lyContentDataLahir.edtAnakKe.text.toString()
            val penolongKelahiran =
                binding.contentEditData.lyContentDataLahir.spinPenolongKelahiran.selectedItemPosition
            val beratLahir =
                binding.contentEditData.lyContentDataLahir.edtBeratLahir.text.toString()
            val panjangLahir =
                binding.contentEditData.lyContentDataLahir.edtPanjangLahir.text.toString()


            val pendidikan =
                binding.contentEditData.lyContentPendidikan.spinPendKK.selectedItemPosition
            val pendidikanSedang =
                binding.contentEditData.lyContentPendidikan.spinPendTempuh.selectedItemPosition
            val pekerjaan =
                binding.contentEditData.lyContentPendidikan.spinPekerjaan.selectedItemPosition

            val suku =
                binding.contentEditData.lyContentKewarganegaraan.spinSuku.selectedItemPosition
            val statusWargaNegara =
                binding.contentEditData.lyContentKewarganegaraan.spinStatusWargaNegara.selectedItemPosition
            val noPaspor =
                binding.contentEditData.lyContentKewarganegaraan.edtNoPaspor.text.toString()
            val tanggalAkhirPaspor =
                binding.contentEditData.lyContentKewarganegaraan.expPasporResult.text.toString()
            val noKitas =
                binding.contentEditData.lyContentKewarganegaraan.edtNoKitas.text.toString()
            val negaraAsal =
                binding.contentEditData.lyContentKewarganegaraan.edtNegaraAsal.text.toString()

            val nikAyah = binding.contentEditData.lyContentOrtu.edtNikAyah.text.toString()
            val namaAyah = binding.contentEditData.lyContentOrtu.edtNamaAyah.text.toString()
            val nikIbu = binding.contentEditData.lyContentOrtu.edtNikIbu.text.toString()
            val namaIbu = binding.contentEditData.lyContentOrtu.edtNamaIbu.text.toString()


            val alamat = binding.contentEditData.lyContentAlamat.edtAlamat.text.toString()
            val dusun =
                when (binding.contentEditData.lyContentAlamat.spinDusun.selectedItemPosition) {
                    1 -> "I"
                    2 -> "II"
                    3 -> "III"
                    4 -> "IV"
                    5 -> "V"
                    else -> ""
                }
            val rw = binding.contentEditData.lyContentAlamat.spinRwKK.selectedItemPosition
            val rt = binding.contentEditData.lyContentAlamat.spinRtKK.selectedItemPosition
            val alamatSebelumnya =
                binding.contentEditData.lyContentAlamat.edtAlamatSblm.text.toString()
            val noTelepon = binding.contentEditData.lyContentAlamat.edtNoTelpon.text.toString()
            val email = binding.contentEditData.lyContentAlamat.edtEmail.text.toString()
            val telegram = binding.contentEditData.lyContentAlamat.edtTelegram.text.toString()
            val caraHubungWarga =
                when (binding.contentEditData.lyContentAlamat.spinCaraHubungWarga.selectedItemPosition) {
                    1 -> "Email"
                    2 -> "Telegram"
                    else -> ""
                }

            val statusPerkawinan =
                binding.contentEditData.lyContentKawin.spinStatusKawin.selectedItemPosition
            val noAktaNikah = binding.contentEditData.lyContentKawin.edtNoAktaNikah.text.toString()
            val tanggalNikah =
                binding.contentEditData.lyContentKawin.tanggalKawinResult.text.toString()
            val noAktaCerai = binding.contentEditData.lyContentKawin.edtAktaCerai.text.toString()
            val tanggalCerai = binding.contentEditData.lyContentKawin.tglCeraiResult.text.toString()

            val golonganDarah =
                binding.contentEditData.lyContentKesehatan.spinGolDarah.selectedItemPosition
            val cacat = binding.contentEditData.lyContentKesehatan.spinCacat.selectedItemPosition
            val sakitMenahun =
                binding.contentEditData.lyContentKesehatan.spinSakitMenahun.selectedItemPosition
            val caraKb =
                binding.contentEditData.lyContentKesehatan.spinAkseptorKb.selectedItemPosition
            val statusKehamilan =
                binding.contentEditData.lyContentKesehatan.spinStatusHamil.selectedItemPosition
            val idAsuransi =
                if (binding.contentEditData.lyContentKesehatan.spinAsrKesehatan.selectedItemPosition == 3) {
                    99
                } else {
                    binding.contentEditData.lyContentKesehatan.spinAsrKesehatan.selectedItemPosition
                }
            val noAsuransi =
                binding.contentEditData.lyContentKesehatan.edtNoAsuransi.text.toString()
            val noBpjsKetenagakerjaan =
                binding.contentEditData.lyContentKesehatan.edtNoBpjs.text.toString()

            val bacaHuruf =
                binding.contentEditData.lyContentLainnya.spinBacaHuruf.selectedItemPosition
            val keterangan = binding.contentEditData.lyContentLainnya.edtKeterangan.text.toString()



            when {
                nik.isEmpty() -> {
                    binding.contentEditData.lyContentDataDiri.edtNik.apply {
                        error = getString(R.string.err_nama_field)
                        requestFocus()
                    }
                    showToast(getString(R.string.err_nik_field))
                }

                nama.isEmpty() -> {
                    binding.contentEditData.lyContentDataDiri.edtNama.apply {
                        error = getString(R.string.err_nama_field)
                        requestFocus()
                    }
                    showToast(getString(R.string.err_nama_field))
                }

                else -> {
                    updateDataPenduduk(
                        nik,
                        nama,
                        statusKtpEl,
                        statusRekam,
                        tagIdCard,
                        tempatCetakKtp,
                        tanggalCetakKtp,
                        noKkSebelumnya,
                        statusHubKeluarga,
                        jenisKelamin,
                        agama,
                        statusPenduduk,
                        noaktaLahir,
                        tempatLahir,
                        tanggalLahir,
                        waktuLahir,
                        tempatDilahirkan,
                        jeniKelahiran,
                        kelahiranAnakKe,
                        penolongKelahiran,
                        beratLahir,
                        panjangLahir,
                        pendidikan,
                        pendidikanSedang,
                        pekerjaan,
                        suku,
                        statusWargaNegara,
                        noPaspor,
                        tanggalAkhirPaspor,
                        noKitas,
                        negaraAsal,
                        nikAyah,
                        namaAyah,
                        nikIbu,
                        namaIbu,
                        alamat,
                        dusun,
                        rw,
                        rt,
                        alamatSebelumnya,
                        noTelepon,
                        email,
                        telegram,
                        caraHubungWarga,
                        statusPerkawinan,
                        noAktaNikah,
                        tanggalNikah,
                        noAktaCerai,
                        tanggalCerai,
                        golonganDarah,
                        cacat,
                        sakitMenahun,
                        caraKb,
                        idAsuransi.toString(),
                        noAsuransi,
                        noBpjsKetenagakerjaan,
                        statusKehamilan,
                        bacaHuruf,
                        keterangan
                    )
                }
            }
        }

        viewModel.updatePendudukResut.observe(this) { resultState ->
            when (resultState) {
                is ResultState.Loading -> {
                    binding.contentLoading.contentLayoutProses.visibility = View.VISIBLE
                }

                is ResultState.Error -> {
                    binding.contentLoading.contentLayoutProses.visibility = View.GONE
                    showToast(resultState.error)
                }

                is ResultState.Success -> {
                    if (resultState.data.kode == 1) {
                        binding.contentLoading.contentLayoutProses.visibility = View.GONE
                        showToast(resultState.data.pesan)
                        val intent = Intent(this, MainUserActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    } else {
                        binding.contentLoading.contentLayoutProses.visibility = View.GONE
                        showToast(getString(R.string.error_update_data))
                    }
                }
            }
        }
    }

    private fun updateDataPenduduk(
        nik: String,
        nama: String,
        statusKtpEl: Int,
        statusRekam: Int,
        tagIdCard: String,
        tempatCetakKtp: String,
        tanggalCetakKtp: String,
        noKkSebelumnya: String,
        statusHubKeluarga: Int,
        jenisKelamin: Int,
        agama: Int,
        statusPenduduk: Int,
        noaktaLahir: String,
        tempatLahir: String,
        tanggalLahir: String,
        waktuLahir: String,
        tempatDilahirkan: Int,
        jeniKelahiran: Int,
        kelahiranAnakKe: String,
        penolongKelahiran: Int,
        beratLahir: String,
        panjangLahir: String,
        pendidikan: Int,
        pendidikanSedang: Int,
        pekerjaan: Int,
        suku: Int,
        statusWargaNegara: Int,
        noPaspor: String,
        tanggalAkhirPaspor: String,
        noKitas: String,
        negaraAsal: String,
        nikAyah: String,
        namaAyah: String,
        nikIbu: String,
        namaIbu: String,
        alamat: String,
        dusun: String,
        rw: Int,
        rt: Int,
        alamatSebelumnya: String,
        noTelepon: String,
        email: String,
        telegram: String,
        caraHubungWarga: String,
        statusPerkawinan: Int,
        noAktaNikah: String,
        tanggalNikah: String,
        noAktaCerai: String,
        tanggalCerai: String,
        golonganDarah: Int,
        cacat: Int,
        sakitMenahun: Int,
        caraKb: Int,
        idAsuransi: String,
        noAsuransi: String,
        noBpjsKetenagakerjaan: String,
        statusKehamilan: Int,
        bacaHuruf: Int,
        keterangan: String
    ) {

        viewModel.updatePenduduk(
            nik,
            nama,
            statusKtpEl.toString(),
            statusRekam.toString(),
            tagIdCard,
            tempatCetakKtp,
            tanggalCetakKtp,
            noKkSebelumnya,
            statusHubKeluarga.toString(),
            jenisKelamin.toString(),
            agama.toString(),
            statusPenduduk.toString(),
            noaktaLahir,
            tempatLahir,
            tanggalLahir,
            waktuLahir,
            tempatDilahirkan.toString(),
            jeniKelahiran.toString(),
            kelahiranAnakKe,
            penolongKelahiran.toString(),
            beratLahir,
            panjangLahir,
            pendidikan.toString(),
            pendidikanSedang.toString(),
            pekerjaan.toString(),
            suku.toString(),
            statusWargaNegara.toString(),
            noPaspor,
            tanggalAkhirPaspor,
            noKitas,
            negaraAsal,
            nikAyah,
            namaAyah,
            nikIbu,
            namaIbu,
            alamat,
            dusun,
            rw.toString(),
            rt.toString(),
            alamatSebelumnya,
            noTelepon,
            email,
            telegram,
            caraHubungWarga,
            statusPerkawinan.toString(),
            noAktaNikah,
            tanggalNikah,
            noAktaCerai,
            tanggalCerai,
            golonganDarah.toString(),
            cacat.toString(),
            sakitMenahun.toString(),
            caraKb.toString(),
            idAsuransi,
            noAsuransi,
            noBpjsKetenagakerjaan,
            statusKehamilan.toString(),
            bacaHuruf.toString(),
            keterangan
        )

    }

    private fun setupDetailDataPenduduk() {
        val nik = intent.getStringExtra(EXTRA_RESULT_NIK)

        nik?.let {
            viewModel.getDetailPenduduk(it)
        }

        viewModel.detailPendudukResult.observe(this) { resultState ->
            when (resultState) {
                is ResultState.Loading -> {
                    binding.apply {
                        contentLoading.contentLayoutProses.visibility = View.VISIBLE
                        contentEditData.editDataLayout.visibility = View.GONE
                    }
                }

                is ResultState.Error -> {

                }

                is ResultState.Success -> {
                    binding.apply {
                        contentEditData.editDataLayout.visibility = View.VISIBLE
                        contentLoading.contentLayoutProses.visibility = View.GONE
                    }

                    val ktpEl = checkIsNull(resultState.data.ktpEl)
                    val statusRekam = checkIsNull(resultState.data.statusRekam)
                    val hubunganDalamKeluarga = checkIsNull(resultState.data.kkLevel)
                    val jenisKelamin = checkIsNull(resultState.data.idSex)
                    val agama = checkIsNull(resultState.data.agamaId)
                    val statusPenduduk = checkIsNull(resultState.data.idStatus)

                    val tempatDilahirkan = checkIsNull(resultState.data.tempatDilahirkan)
                    val jenisKelahiran = checkIsNull(resultState.data.jenisKelahiran)
                    val penolongKelahiran = checkIsNull(resultState.data.penolongKelahiran)

                    val pendidikan = checkIsNull(resultState.data.pendidikanKkId)
                    val pendidikanSedang = checkIsNull(resultState.data.pendidikanSedangId)
                    val pekerjaan = checkIsNull(resultState.data.pekerjaanId)

                    val suku = checkIsNull(resultState.data.suku)
                    val wargaNegara = checkIsNull(resultState.data.warganegaraId)

                    val dusun = resultState.data.dusun
                    val dusunArray = resources.getStringArray(R.array.dusun)
                    val selectedDusunIndex = if (dusun in dusunArray) {
                        dusunArray.indexOf(dusun)
                    } else {
                        0
                    }

                    val caraHubungWarga = resultState.data.hubungWarga
                    val caraHubungWargaArray =
                        resources.getStringArray(R.array.cara_hubung_warga)
                    val selectedCaraHubungIndex =
                        if (caraHubungWarga in caraHubungWargaArray) {
                            caraHubungWargaArray.indexOf(caraHubungWarga)
                        } else {
                            0
                        }

                    val statusKawin = checkIsNull(resultState.data.statusKawin)

                    val golonganDarah = checkIsNull(resultState.data.golonganDarahId)
                    val cacat = checkIsNull(resultState.data.cacatId)
                    val sakitMenahun = checkIsNull(resultState.data.sakitMenahunId)
                    val caraKB = checkIsNull(resultState.data.caraKbId)
                    val asuransi = checkIsNull(resultState.data.idAsuransi)
                    val hamil = checkIsNull(resultState.data.hamil)

                    val bahasa = checkIsNull(resultState.data.bahasaId)

                    // DATA DIRI
                    binding.contentEditData.lyContentDataDiri.apply {
                        edtNik.setText(nik)
                        edtNama.setText(resultState.data.nama)
                        edtTagIdCard.setText(resultState.data.tagIdCard)
                        edtTmptPenerbitanKtp.setText(resultState.data.tempatCetakKtp)
                        edtNoKKSblm.setText(resultState.data.noKkSebelumnya)

                        tglPenerbitanKtpResult.text = resultState.data.tanggalCetakKtp

                        spinIdentitasEl.setSelection(ktpEl.toInt())
                        spinStatusRekam.setSelection(statusRekam.toInt())
                        spinHubKeluarga.setSelection(hubunganDalamKeluarga.toInt())
                        spinJenisKelamin.setSelection(jenisKelamin.toInt())
                        spinAgama.setSelection(agama.toInt())
                        spinStatusPenduduk.setSelection(statusPenduduk.toInt())
                    }

                    // DATA KELAHIRAN
                    binding.contentEditData.lyContentDataLahir.apply {
                        edtNoAkta.setText(resultState.data.aktaLahir)
                        edtTempatLahir.setText(resultState.data.tempatlahir)
                        edtAnakKe.setText(resultState.data.kelahiranAnakKe)
                        edtBeratLahir.setText(resultState.data.beratLahir)
                        edtPanjangLahir.setText(resultState.data.panjangLahir)


                        tglLahirResult.text = resultState.data.tanggallahir
                        waktuLahirResult.text = resultState.data.waktuLahir

                        spinTempatDilahirkan.setSelection(tempatDilahirkan.toInt())
                        spinJenisKelahiran.setSelection(jenisKelahiran.toInt())
                        spinPenolongKelahiran.setSelection(penolongKelahiran.toInt())
                    }

                    // DATA PENDIDIKAN dan PEKERJAAN
                    binding.contentEditData.lyContentPendidikan.apply {
                        spinPendKK.setSelection(pendidikan.toInt())
                        spinPendTempuh.setSelection(pendidikanSedang.toInt())
                        spinPekerjaan.setSelection(pekerjaan.toInt())

                    }

                    // DATA KEWARGANEGARAAN
                    binding.contentEditData.lyContentKewarganegaraan.apply {
                        spinSuku.setSelection(suku.toInt())

                        spinStatusWargaNegara.setSelection(wargaNegara.toInt())

                        edtNoPaspor.setText(resultState.data.dokumenPasport)
                        edtNoKitas.setText(resultState.data.dokumenKitas)
                        edtNegaraAsal.setText(resultState.data.negaraAsal)

                        expPasporResult.text = resultState.data.tanggalAkhirPaspor

                    }

                    // DATA ORTU
                    binding.contentEditData.lyContentOrtu.apply {
                        edtNikAyah.setText(resultState.data.ayahNik)
                        edtNamaAyah.setText(resultState.data.namaAyah)
                        edtNikIbu.setText(resultState.data.ibuNik)
                        edtNamaIbu.setText(resultState.data.namaIbu)
                    }

                    // DATA ALAMAT
                    binding.contentEditData.lyContentAlamat.apply {
                        edtAlamat.setText(resultState.data.alamat)
                        edtAlamatSblm.setText(resultState.data.alamatSebelumnya)
                        edtNoTelpon.setText(resultState.data.telepon)
                        edtEmail.setText(resultState.data.email)
                        edtTelegram.setText(resultState.data.telegram)

                        spinDusun.setSelection(selectedDusunIndex)
                        spinCaraHubungWarga.setSelection(selectedCaraHubungIndex)
                    }

                    // DATA KAWIN
                    binding.contentEditData.lyContentKawin.apply {
                        spinStatusKawin.setSelection(statusKawin.toInt())

                        edtNoAktaNikah.setText(resultState.data.aktaPerkawinan)
                        edtAktaCerai.setText(resultState.data.aktaPerceraian)

                        tanggalKawinResult.text = resultState.data.tanggalperkawinan
                        tglCeraiResult.text = resultState.data.tanggalperceraian

                    }

                    // DATA KESEHATAN
                    binding.contentEditData.lyContentKesehatan.apply {
                        spinGolDarah.setSelection(golonganDarah.toInt())
                        spinCacat.setSelection(cacat.toInt())
                        spinSakitMenahun.setSelection(sakitMenahun.toInt())
                        spinAkseptorKb.setSelection(caraKB.toInt())
                        spinAsrKesehatan.setSelection(asuransi.toInt())
                        spinStatusHamil.setSelection(hamil.toInt())


                        edtNoAsuransi.setText(resultState.data.noAsuransi)
                        edtNoBpjs.setText(resultState.data.bpjsKetenagakerjaan)
                    }

                    // DATA LAINNYA
                    binding.contentEditData.lyContentLainnya.apply {
                        spinBacaHuruf.setSelection(bahasa.toInt())

                        edtKeterangan.setText(resultState.data.ket)
                    }
                }
            }
        }
    }

    private fun setupAction() {
        binding.contentEditData.lyContentDataDiri.btnTglPenerbitanKtp.setOnClickListener {
            showDatePicker(PENERBITAN_KTP_DATE_PICKER)
        }

        binding.contentEditData.lyContentDataLahir.btnTglLahir.setOnClickListener {
            showDatePicker(TANGGAL_LAHIR_DATE_PICKER)
        }

        binding.contentEditData.lyContentDataLahir.btnWaktuLahir.setOnClickListener {
            showTimePicker(it)
        }

        binding.contentEditData.lyContentKewarganegaraan.btnExpPaspor.setOnClickListener {
            showDatePicker(EXP_PASPOR_DATE_PICKER)
        }


        binding.contentEditData.lyContentKawin.btnTanggalKawin.setOnClickListener {
            showDatePicker(TANGGAL_KAWIN_DATE_PICKER)
        }

        binding.contentEditData.lyContentKawin.btnTglCerai.setOnClickListener {
            showDatePicker(TANGGAL_CERAI_DATE_PICKER)
        }
    }

    private fun setupViewSpinner() {
        setupSpinner(
            binding.contentEditData.lyContentDataDiri.spinIdentitasEl,
            R.array.identitas_el
        )
        setupOnItemSelectedListener(
            binding.contentEditData.lyContentDataDiri.spinIdentitasEl,
            R.array.identitas_el
        )

        setupSpinner(
            binding.contentEditData.lyContentDataDiri.spinStatusRekam,
            R.array.status_rekam
        )
        setupOnItemSelectedListener(
            binding.contentEditData.lyContentDataDiri.spinStatusRekam,
            R.array.status_rekam
        )

        setupSpinner(
            binding.contentEditData.lyContentDataDiri.spinHubKeluarga,
            R.array.hubungan_keluarga
        )
        setupOnItemSelectedListener(
            binding.contentEditData.lyContentDataDiri.spinHubKeluarga,
            R.array.hubungan_keluarga
        )

        setupSpinner(
            binding.contentEditData.lyContentDataDiri.spinJenisKelamin,
            R.array.jenis_kelamin
        )
        setupOnItemSelectedListener(
            binding.contentEditData.lyContentDataDiri.spinJenisKelamin,
            R.array.jenis_kelamin
        )
        setupSpinner(binding.contentEditData.lyContentDataDiri.spinAgama, R.array.agama)
        setupOnItemSelectedListener(
            binding.contentEditData.lyContentDataDiri.spinAgama,
            R.array.agama
        )

        setupSpinner(
            binding.contentEditData.lyContentDataDiri.spinStatusPenduduk,
            R.array.status_penduduk
        )
        setupOnItemSelectedListener(
            binding.contentEditData.lyContentDataDiri.spinStatusPenduduk,
            R.array.status_penduduk
        )

        setupSpinner(
            binding.contentEditData.lyContentDataLahir.spinTempatDilahirkan,
            R.array.tempat_lahir
        )
        setupOnItemSelectedListener(
            binding.contentEditData.lyContentDataLahir.spinTempatDilahirkan,
            R.array.tempat_lahir
        )

        setupSpinner(
            binding.contentEditData.lyContentDataLahir.spinJenisKelahiran,
            R.array.jenis_kelahiran
        )
        setupOnItemSelectedListener(
            binding.contentEditData.lyContentDataLahir.spinJenisKelahiran,
            R.array.jenis_kelahiran
        )

        setupSpinner(
            binding.contentEditData.lyContentDataLahir.spinPenolongKelahiran,
            R.array.penolong_kelahiran
        )
        setupOnItemSelectedListener(
            binding.contentEditData.lyContentDataLahir.spinPenolongKelahiran,
            R.array.penolong_kelahiran
        )


        setupSpinner(binding.contentEditData.lyContentPendidikan.spinPendKK, R.array.pendidikan)
        setupOnItemSelectedListener(
            binding.contentEditData.lyContentPendidikan.spinPendKK,
            R.array.pendidikan
        )

        setupSpinner(
            binding.contentEditData.lyContentPendidikan.spinPendTempuh,
            R.array.pendidikan_sedang_dijalani
        )
        setupOnItemSelectedListener(
            binding.contentEditData.lyContentPendidikan.spinPendTempuh,
            R.array.pendidikan_sedang_dijalani
        )
        setupSpinner(binding.contentEditData.lyContentPendidikan.spinPekerjaan, R.array.pekerjaan)
        setupOnItemSelectedListener(
            binding.contentEditData.lyContentPendidikan.spinPekerjaan,
            R.array.pekerjaan
        )

        setupSpinner(binding.contentEditData.lyContentKewarganegaraan.spinSuku, R.array.suku)
        setupOnItemSelectedListener(
            binding.contentEditData.lyContentKewarganegaraan.spinSuku,
            R.array.suku
        )

        setupSpinner(
            binding.contentEditData.lyContentKewarganegaraan.spinStatusWargaNegara,
            R.array.kewarnegaraan
        )
        setupOnItemSelectedListener(
            binding.contentEditData.lyContentKewarganegaraan.spinStatusWargaNegara,
            R.array.kewarnegaraan
        )

        setupSpinner(binding.contentEditData.lyContentAlamat.spinDusun, R.array.dusun)
        setupOnItemSelectedListener(
            binding.contentEditData.lyContentAlamat.spinDusun,
            R.array.dusun
        )

        setupSpinner(
            binding.contentEditData.lyContentAlamat.spinCaraHubungWarga,
            R.array.cara_hubung_warga
        )
        setupOnItemSelectedListener(
            binding.contentEditData.lyContentAlamat.spinCaraHubungWarga,
            R.array.cara_hubung_warga
        )

        setupSpinner(binding.contentEditData.lyContentKawin.spinStatusKawin, R.array.status_kawin)
        setupOnItemSelectedListener(
            binding.contentEditData.lyContentKawin.spinStatusKawin,
            R.array.status_kawin
        )

        setupSpinner(
            binding.contentEditData.lyContentKesehatan.spinGolDarah,
            R.array.golongan_darah
        )
        setupOnItemSelectedListener(
            binding.contentEditData.lyContentKesehatan.spinGolDarah,
            R.array.golongan_darah
        )

        setupSpinner(binding.contentEditData.lyContentKesehatan.spinCacat, R.array.jenis_cacat)
        setupOnItemSelectedListener(
            binding.contentEditData.lyContentKesehatan.spinCacat,
            R.array.jenis_cacat
        )

        setupSpinner(
            binding.contentEditData.lyContentKesehatan.spinSakitMenahun,
            R.array.sakit_menahun
        )
        setupOnItemSelectedListener(
            binding.contentEditData.lyContentKesehatan.spinSakitMenahun,
            R.array.sakit_menahun
        )

        setupSpinner(binding.contentEditData.lyContentKesehatan.spinAkseptorKb, R.array.cara_kb)
        setupOnItemSelectedListener(
            binding.contentEditData.lyContentKesehatan.spinAkseptorKb,
            R.array.cara_kb
        )

        setupSpinner(binding.contentEditData.lyContentKesehatan.spinAsrKesehatan, R.array.asuransi)
        setupOnItemSelectedListener(
            binding.contentEditData.lyContentKesehatan.spinAsrKesehatan,
            R.array.asuransi
        )

        setupSpinner(
            binding.contentEditData.lyContentKesehatan.spinStatusHamil,
            R.array.status_kehamilan
        )
        setupOnItemSelectedListener(
            binding.contentEditData.lyContentKesehatan.spinStatusHamil,
            R.array.status_kehamilan
        )

        setupSpinner(binding.contentEditData.lyContentLainnya.spinBacaHuruf, R.array.bahasa)
        setupOnItemSelectedListener(
            binding.contentEditData.lyContentLainnya.spinBacaHuruf,
            R.array.bahasa
        )
    }

    private fun setupSpinner(spinner: Spinner, arrayResId: Int) {
        val dataArray = resources.getStringArray(arrayResId)

        val adapter =
            object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dataArray) {
                override fun isEnabled(position: Int): Boolean {
                    return position != 0
                }

                override fun getDropDownView(
                    position: Int,
                    convertView: View?,
                    parent: ViewGroup
                ): View {
                    val view = super.getDropDownView(position, convertView, parent) as TextView
                    if (position == 0) {
                        view.setTextColor(Color.GRAY)
                    } else {
                        view.setTextColor(Color.BLACK)
                    }
                    return view
                }
            }

        spinner.adapter = adapter

    }

    private fun setupOnItemSelectedListener(spinner: Spinner, arrayResId: Int) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val value = parent!!.getItemAtPosition(position).toString()
                if (value == resources.getStringArray(arrayResId)[0]) {
                    (view as TextView).setTextColor(Color.GRAY)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }
    }

    private fun showDatePicker(tag: String?) {
        val dialogFragment = DatePickerFragment()
        dialogFragment.show(supportFragmentManager, tag)
    }

    private fun checkIsNull(value: String?) =
        if (value.isNullOrEmpty() || value == "null") "0" else value


    private fun showTimePicker(view: View) {
        val dialogFragment = TimePickerFragment()
        dialogFragment.show(supportFragmentManager, "timePicker")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        when (tag) {
            PENERBITAN_KTP_DATE_PICKER -> {
                binding.contentEditData.lyContentDataDiri.tglPenerbitanKtpResult.text =
                    dateFormat.format(calendar.time)
            }

            TANGGAL_LAHIR_DATE_PICKER -> {
                binding.contentEditData.lyContentDataLahir.tglLahirResult.text =
                    dateFormat.format(calendar.time)
            }

            EXP_PASPOR_DATE_PICKER -> {
                binding.contentEditData.lyContentKewarganegaraan.expPasporResult.text =
                    dateFormat.format(calendar.time)
            }

            TANGGAL_KAWIN_DATE_PICKER -> {
                binding.contentEditData.lyContentKawin.tanggalKawinResult.text =
                    dateFormat.format(calendar.time)
            }

            TANGGAL_CERAI_DATE_PICKER -> {
                binding.contentEditData.lyContentKawin.tglCeraiResult.text =
                    dateFormat.format(calendar.time)
            }
        }
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        binding.contentEditData.lyContentDataLahir.waktuLahirResult.text =
            dateFormat.format(calendar.time)
    }
}
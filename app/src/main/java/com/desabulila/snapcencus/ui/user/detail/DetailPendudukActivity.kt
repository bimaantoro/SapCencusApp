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
        binding.contentDetailData.btnSave.setOnClickListener {
            val nik = binding.contentDetailData.contentDataDiri.edtNik.text.toString().trim()
            val nama = binding.contentDetailData.contentDataDiri.edtNama.text.toString()
            val statusKtpEl =
                binding.contentDetailData.contentDataDiri.spinIdentitasEl.selectedItemPosition
            val statusRekam =
                binding.contentDetailData.contentDataDiri.spinStatusRekam.selectedItemPosition
            val tagIdCard =
                binding.contentDetailData.contentDataDiri.edtTagIdCard.text.toString().trim()
            val tempatCetakKtp =
                binding.contentDetailData.contentDataDiri.edtTmptPenerbitanKtp.text.toString()
            val tanggalCetakKtp =
                binding.contentDetailData.contentDataDiri.tglPenerbitanKtpResult.text.toString()
            val noKkSebelumnya =
                binding.contentDetailData.contentDataDiri.edtNoKKSblm.text.toString().trim()
            val statusHubKeluarga =
                binding.contentDetailData.contentDataDiri.spinHubKeluarga.selectedItemPosition
            val jenisKelamin =
                binding.contentDetailData.contentDataDiri.spinJenisKelamin.selectedItemPosition
            val agama = binding.contentDetailData.contentDataDiri.spinAgama.selectedItemPosition
            val statusPenduduk =
                binding.contentDetailData.contentDataDiri.spinStatusPenduduk.selectedItemPosition


            val noaktaLahir =
                binding.contentDetailData.contentDataLahir.edtNoAkta.text.toString().trim()
            val tempatLahir =
                binding.contentDetailData.contentDataLahir.edtTempatLahir.text.toString()
            val tanggalLahir =
                binding.contentDetailData.contentDataLahir.tglLahirResult.text.toString()
            val waktuLahir =
                binding.contentDetailData.contentDataLahir.waktuLahirResult.text.toString()
            val tempatDilahirkan =
                binding.contentDetailData.contentDataLahir.spinTempatDilahirkan.selectedItemPosition
            val jeniKelahiran =
                binding.contentDetailData.contentDataLahir.spinJenisKelahiran.selectedItemPosition
            val kelahiranAnakKe =
                binding.contentDetailData.contentDataLahir.edtAnakKe.text.toString()
            val penolongKelahiran =
                binding.contentDetailData.contentDataLahir.spinPenolongKelahiran.selectedItemPosition
            val beratLahir =
                binding.contentDetailData.contentDataLahir.edtBeratLahir.text.toString()
            val panjangLahir =
                binding.contentDetailData.contentDataLahir.edtPanjangLahir.text.toString()


            val pendidikan =
                binding.contentDetailData.contentDataPendidikanPekerjaan.spinPendKK.selectedItemPosition
            val pendidikanSedang =
                binding.contentDetailData.contentDataPendidikanPekerjaan.spinPendTempuh.selectedItemPosition
            val pekerjaan =
                binding.contentDetailData.contentDataPendidikanPekerjaan.spinPekerjaan.selectedItemPosition

            val suku =
                binding.contentDetailData.contentDataKewarganegaraan.spinSuku.selectedItemPosition
            val statusWargaNegara =
                binding.contentDetailData.contentDataKewarganegaraan.spinStatusWargaNegara.selectedItemPosition
            val noPaspor =
                binding.contentDetailData.contentDataKewarganegaraan.edtNoPaspor.text.toString()
            val tanggalAkhirPaspor =
                binding.contentDetailData.contentDataKewarganegaraan.expPasporResult.text.toString()
            val noKitas =
                binding.contentDetailData.contentDataKewarganegaraan.edtNoKitas.text.toString()
            val negaraAsal =
                binding.contentDetailData.contentDataKewarganegaraan.edtNegaraAsal.text.toString()

            val nikAyah = binding.contentDetailData.contentDataOrtu.edtNikAyah.text.toString()
            val namaAyah = binding.contentDetailData.contentDataOrtu.edtNamaAyah.text.toString()
            val nikIbu = binding.contentDetailData.contentDataOrtu.edtNikIbu.text.toString()
            val namaIbu = binding.contentDetailData.contentDataOrtu.edtNamaIbu.text.toString()


            val alamat = binding.contentDetailData.contentDataAlamat.edtAlamat.text.toString()
            val dusun =
                when (binding.contentDetailData.contentDataAlamat.spinDusun.selectedItemPosition) {
                    1 -> "I"
                    2 -> "II"
                    3 -> "III"
                    4 -> "IV"
                    5 -> "V"
                    else -> ""
                }
            val rw = binding.contentDetailData.contentDataAlamat.spinRwKK.selectedItemPosition
            val rt = binding.contentDetailData.contentDataAlamat.spinRtKK.selectedItemPosition
            val alamatSebelumnya =
                binding.contentDetailData.contentDataAlamat.edtAlamatSblm.text.toString()
            val noTelepon = binding.contentDetailData.contentDataAlamat.edtNoTelpon.text.toString()
            val email = binding.contentDetailData.contentDataAlamat.edtEmail.text.toString()
            val telegram = binding.contentDetailData.contentDataAlamat.edtTelegram.text.toString()
            val caraHubungWarga =
                when (binding.contentDetailData.contentDataAlamat.spinCaraHubungWarga.selectedItemPosition) {
                    1 -> "Email"
                    2 -> "Telegram"
                    else -> ""
                }

            val statusPerkawinan =
                binding.contentDetailData.contentDataPerkawinan.spinStatusKawin.selectedItemPosition
            val noAktaNikah =
                binding.contentDetailData.contentDataPerkawinan.edtNoAktaNikah.text.toString()
            val tanggalNikah =
                binding.contentDetailData.contentDataPerkawinan.tanggalKawinResult.text.toString()
            val noAktaCerai =
                binding.contentDetailData.contentDataPerkawinan.edtAktaCerai.text.toString()
            val tanggalCerai =
                binding.contentDetailData.contentDataPerkawinan.tglCeraiResult.text.toString()

            val golonganDarah =
                binding.contentDetailData.contentDataKesehatan.spinGolDarah.selectedItemPosition
            val cacat = binding.contentDetailData.contentDataKesehatan.spinCacat.selectedItemPosition
            val sakitMenahun =
                binding.contentDetailData.contentDataKesehatan.spinSakitMenahun.selectedItemPosition
            val caraKb =
                binding.contentDetailData.contentDataKesehatan.spinAkseptorKb.selectedItemPosition
            val statusKehamilan =
                binding.contentDetailData.contentDataKesehatan.spinStatusHamil.selectedItemPosition
            val idAsuransi =
                if (binding.contentDetailData.contentDataKesehatan.spinAsrKesehatan.selectedItemPosition == 3) {
                    99
                } else {
                    binding.contentDetailData.contentDataKesehatan.spinAsrKesehatan.selectedItemPosition
                }
            val noAsuransi =
                binding.contentDetailData.contentDataKesehatan.edtNoAsuransi.text.toString()
            val noBpjsKetenagakerjaan =
                binding.contentDetailData.contentDataKesehatan.edtNoBpjs.text.toString()

            val bacaHuruf =
                binding.contentDetailData.contentDataLainnya.spinBacaHuruf.selectedItemPosition
            val keterangan =
                binding.contentDetailData.contentDataLainnya.edtKeterangan.text.toString()



            when {
                nik.isEmpty() -> {
                    binding.contentDetailData.contentDataDiri.edtNik.apply {
                        error = getString(R.string.err_nama_field)
                        requestFocus()
                    }
                    showToast(getString(R.string.err_nik_field))
                }

                nama.isEmpty() -> {
                    binding.contentDetailData.contentDataDiri.edtNama.apply {
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
                    binding.itemDialogLoading.loadingLayout.visibility = View.VISIBLE
                }

                is ResultState.Error -> {
                    binding.itemDialogLoading.loadingLayout.visibility = View.GONE
                    showToast(resultState.message.toString())
                }

                is ResultState.Success -> {
                    if (resultState.data?.kode == 1) {
                        binding.itemDialogLoading.loadingLayout.visibility = View.GONE
                        showToast(resultState.data.pesan)
                        val intent = Intent(this, MainUserActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    } else {
                        binding.itemDialogLoading.loadingLayout.visibility = View.GONE
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
                        itemDialogLoading.loadingLayout.visibility = View.VISIBLE
                        contentDetailData.editDataLayout.visibility = View.GONE
                    }
                }

                is ResultState.Error -> {

                }

                is ResultState.Success -> {
                    binding.apply {
                        contentDetailData.editDataLayout.visibility = View.VISIBLE
                        itemDialogLoading.loadingLayout.visibility = View.GONE
                    }

                    val ktpEl = checkIsNull(resultState.data?.ktpEl)
                    val statusRekam = checkIsNull(resultState.data?.statusRekam)
                    val hubunganDalamKeluarga = checkIsNull(resultState.data?.kkLevel)
                    val jenisKelamin = checkIsNull(resultState.data?.idSex)
                    val agama = checkIsNull(resultState.data?.agamaId)
                    val statusPenduduk = checkIsNull(resultState.data?.idStatus)

                    val tempatDilahirkan = checkIsNull(resultState.data?.tempatDilahirkan)
                    val jenisKelahiran = checkIsNull(resultState.data?.jenisKelahiran)
                    val penolongKelahiran = checkIsNull(resultState.data?.penolongKelahiran)

                    val pendidikan = checkIsNull(resultState.data?.pendidikanKkId)
                    val pendidikanSedang = checkIsNull(resultState.data?.pendidikanSedangId)
                    val pekerjaan = checkIsNull(resultState.data?.pekerjaanId)

                    val suku = checkIsNull(resultState.data?.suku)
                    val wargaNegara = checkIsNull(resultState.data?.warganegaraId)

                    val dusun = resultState.data?.dusun
                    val dusunArray = resources.getStringArray(R.array.dusun)
                    val selectedDusunIndex = if (dusun in dusunArray) {
                        dusunArray.indexOf(dusun)
                    } else {
                        0
                    }

                    val caraHubungWarga = resultState.data?.hubungWarga
                    val caraHubungWargaArray =
                        resources.getStringArray(R.array.cara_hubung_warga)
                    val selectedCaraHubungIndex =
                        if (caraHubungWarga in caraHubungWargaArray) {
                            caraHubungWargaArray.indexOf(caraHubungWarga)
                        } else {
                            0
                        }

                    val statusKawin = checkIsNull(resultState.data?.statusKawin)

                    val golonganDarah = checkIsNull(resultState.data?.golonganDarahId)
                    val cacat = checkIsNull(resultState.data?.cacatId)
                    val sakitMenahun = checkIsNull(resultState.data?.sakitMenahunId)
                    val caraKB = checkIsNull(resultState.data?.caraKbId)
                    val asuransi = checkIsNull(resultState.data?.idAsuransi)
                    val hamil = checkIsNull(resultState.data?.hamil)

                    val bahasa = checkIsNull(resultState.data?.bahasaId)

                    // DATA DIRI
                    binding.contentDetailData.contentDataDiri.apply {
                        edtNik.setText(nik)
                        edtNama.setText(resultState.data?.nama)
                        edtTagIdCard.setText(resultState.data?.tagIdCard)
                        edtTmptPenerbitanKtp.setText(resultState.data?.tempatCetakKtp)
                        edtNoKKSblm.setText(resultState.data?.noKkSebelumnya)

                        tglPenerbitanKtpResult.text = resultState.data?.tanggalCetakKtp

                        spinIdentitasEl.setSelection(ktpEl.toInt())
                        spinStatusRekam.setSelection(statusRekam.toInt())
                        spinHubKeluarga.setSelection(hubunganDalamKeluarga.toInt())
                        spinJenisKelamin.setSelection(jenisKelamin.toInt())
                        spinAgama.setSelection(agama.toInt())
                        spinStatusPenduduk.setSelection(statusPenduduk.toInt())
                    }

                    // DATA KELAHIRAN
                    binding.contentDetailData.contentDataLahir.apply {
                        edtNoAkta.setText(resultState.data?.aktaLahir)
                        edtTempatLahir.setText(resultState.data?.tempatlahir)
                        edtAnakKe.setText(resultState.data?.kelahiranAnakKe)
                        edtBeratLahir.setText(resultState.data?.beratLahir)
                        edtPanjangLahir.setText(resultState.data?.panjangLahir)


                        tglLahirResult.text = resultState.data?.tanggallahir
                        waktuLahirResult.text = resultState.data?.waktuLahir

                        spinTempatDilahirkan.setSelection(tempatDilahirkan.toInt())
                        spinJenisKelahiran.setSelection(jenisKelahiran.toInt())
                        spinPenolongKelahiran.setSelection(penolongKelahiran.toInt())
                    }

                    // DATA PENDIDIKAN dan PEKERJAAN
                    binding.contentDetailData.contentDataPendidikanPekerjaan.apply {
                        spinPendKK.setSelection(pendidikan.toInt())
                        spinPendTempuh.setSelection(pendidikanSedang.toInt())
                        spinPekerjaan.setSelection(pekerjaan.toInt())

                    }

                    // DATA KEWARGANEGARAAN
                    binding.contentDetailData.contentDataKewarganegaraan.apply {
                        spinSuku.setSelection(suku.toInt())

                        spinStatusWargaNegara.setSelection(wargaNegara.toInt())

                        edtNoPaspor.setText(resultState.data?.dokumenPasport)
                        edtNoKitas.setText(resultState.data?.dokumenKitas)
                        edtNegaraAsal.setText(resultState.data?.negaraAsal)

                        expPasporResult.text = resultState.data?.tanggalAkhirPaspor

                    }

                    // DATA ORTU
                    binding.contentDetailData.contentDataOrtu.apply {
                        edtNikAyah.setText(resultState.data?.ayahNik)
                        edtNamaAyah.setText(resultState.data?.namaAyah)
                        edtNikIbu.setText(resultState.data?.ibuNik)
                        edtNamaIbu.setText(resultState.data?.namaIbu)
                    }

                    // DATA ALAMAT
                    binding.contentDetailData.contentDataAlamat.apply {
                        edtAlamat.setText(resultState.data?.alamat)
                        edtAlamatSblm.setText(resultState.data?.alamatSebelumnya)
                        edtNoTelpon.setText(resultState.data?.telepon)
                        edtEmail.setText(resultState.data?.email)
                        edtTelegram.setText(resultState.data?.telegram)

                        spinDusun.setSelection(selectedDusunIndex)
                        spinCaraHubungWarga.setSelection(selectedCaraHubungIndex)
                    }

                    // DATA KAWIN
                    binding.contentDetailData.contentDataPerkawinan.apply {
                        spinStatusKawin.setSelection(statusKawin.toInt())

                        edtNoAktaNikah.setText(resultState.data?.aktaPerkawinan)
                        edtAktaCerai.setText(resultState.data?.aktaPerceraian)

                        tanggalKawinResult.text = resultState.data?.tanggalperkawinan
                        tglCeraiResult.text = resultState.data?.tanggalperceraian

                    }

                    // DATA KESEHATAN
                    binding.contentDetailData.contentDataKesehatan.apply {
                        spinGolDarah.setSelection(golonganDarah.toInt())
                        spinCacat.setSelection(cacat.toInt())
                        spinSakitMenahun.setSelection(sakitMenahun.toInt())
                        spinAkseptorKb.setSelection(caraKB.toInt())
                        spinAsrKesehatan.setSelection(asuransi.toInt())
                        spinStatusHamil.setSelection(hamil.toInt())


                        edtNoAsuransi.setText(resultState.data?.noAsuransi)
                        edtNoBpjs.setText(resultState.data?.bpjsKetenagakerjaan)
                    }

                    // DATA LAINNYA
                    binding.contentDetailData.contentDataLainnya.apply {
                        spinBacaHuruf.setSelection(bahasa.toInt())

                        edtKeterangan.setText(resultState.data?.ket)
                    }
                }
            }
        }
    }

    private fun setupAction() {
        binding.contentDetailData.contentDataDiri.btnTglPenerbitanKtp.setOnClickListener {
            showDatePicker(PENERBITAN_KTP_DATE_PICKER)
        }

        binding.contentDetailData.contentDataLahir.btnTglLahir.setOnClickListener {
            showDatePicker(TANGGAL_LAHIR_DATE_PICKER)
        }

        binding.contentDetailData.contentDataLahir.btnWaktuLahir.setOnClickListener {
            showTimePicker(it)
        }

        binding.contentDetailData.contentDataKewarganegaraan.btnExpPaspor.setOnClickListener {
            showDatePicker(EXP_PASPOR_DATE_PICKER)
        }


        binding.contentDetailData.contentDataPerkawinan.btnTanggalKawin.setOnClickListener {
            showDatePicker(TANGGAL_KAWIN_DATE_PICKER)
        }

        binding.contentDetailData.contentDataPerkawinan.btnTglCerai.setOnClickListener {
            showDatePicker(TANGGAL_CERAI_DATE_PICKER)
        }
    }

    private fun setupViewSpinner() {
        setupSpinner(
            binding.contentDetailData.contentDataDiri.spinIdentitasEl,
            R.array.identitas_el
        )
        setupOnItemSelectedListener(
            binding.contentDetailData.contentDataDiri.spinIdentitasEl,
            R.array.identitas_el
        )

        setupSpinner(
            binding.contentDetailData.contentDataDiri.spinStatusRekam,
            R.array.status_rekam
        )
        setupOnItemSelectedListener(
            binding.contentDetailData.contentDataDiri.spinStatusRekam,
            R.array.status_rekam
        )

        setupSpinner(
            binding.contentDetailData.contentDataDiri.spinHubKeluarga,
            R.array.hubungan_keluarga
        )
        setupOnItemSelectedListener(
            binding.contentDetailData.contentDataDiri.spinHubKeluarga,
            R.array.hubungan_keluarga
        )

        setupSpinner(
            binding.contentDetailData.contentDataDiri.spinJenisKelamin,
            R.array.jenis_kelamin
        )
        setupOnItemSelectedListener(
            binding.contentDetailData.contentDataDiri.spinJenisKelamin,
            R.array.jenis_kelamin
        )
        setupSpinner(binding.contentDetailData.contentDataDiri.spinAgama, R.array.agama)
        setupOnItemSelectedListener(
            binding.contentDetailData.contentDataDiri.spinAgama,
            R.array.agama
        )

        setupSpinner(
            binding.contentDetailData.contentDataDiri.spinStatusPenduduk,
            R.array.status_penduduk
        )
        setupOnItemSelectedListener(
            binding.contentDetailData.contentDataDiri.spinStatusPenduduk,
            R.array.status_penduduk
        )

        setupSpinner(
            binding.contentDetailData.contentDataLahir.spinTempatDilahirkan,
            R.array.tempat_lahir
        )
        setupOnItemSelectedListener(
            binding.contentDetailData.contentDataLahir.spinTempatDilahirkan,
            R.array.tempat_lahir
        )

        setupSpinner(
            binding.contentDetailData.contentDataLahir.spinJenisKelahiran,
            R.array.jenis_kelahiran
        )
        setupOnItemSelectedListener(
            binding.contentDetailData.contentDataLahir.spinJenisKelahiran,
            R.array.jenis_kelahiran
        )

        setupSpinner(
            binding.contentDetailData.contentDataLahir.spinPenolongKelahiran,
            R.array.penolong_kelahiran
        )
        setupOnItemSelectedListener(
            binding.contentDetailData.contentDataLahir.spinPenolongKelahiran,
            R.array.penolong_kelahiran
        )


        setupSpinner(
            binding.contentDetailData.contentDataPendidikanPekerjaan.spinPendKK,
            R.array.pendidikan
        )
        setupOnItemSelectedListener(
            binding.contentDetailData.contentDataPendidikanPekerjaan.spinPendKK,
            R.array.pendidikan
        )

        setupSpinner(
            binding.contentDetailData.contentDataPendidikanPekerjaan.spinPendTempuh,
            R.array.pendidikan_sedang_dijalani
        )
        setupOnItemSelectedListener(
            binding.contentDetailData.contentDataPendidikanPekerjaan.spinPendTempuh,
            R.array.pendidikan_sedang_dijalani
        )
        setupSpinner(
            binding.contentDetailData.contentDataPendidikanPekerjaan.spinPekerjaan,
            R.array.pekerjaan
        )
        setupOnItemSelectedListener(
            binding.contentDetailData.contentDataPendidikanPekerjaan.spinPekerjaan,
            R.array.pekerjaan
        )

        setupSpinner(binding.contentDetailData.contentDataKewarganegaraan.spinSuku, R.array.suku)
        setupOnItemSelectedListener(
            binding.contentDetailData.contentDataKewarganegaraan.spinSuku,
            R.array.suku
        )

        setupSpinner(
            binding.contentDetailData.contentDataKewarganegaraan.spinStatusWargaNegara,
            R.array.kewarnegaraan
        )
        setupOnItemSelectedListener(
            binding.contentDetailData.contentDataKewarganegaraan.spinStatusWargaNegara,
            R.array.kewarnegaraan
        )

        setupSpinner(binding.contentDetailData.contentDataAlamat.spinDusun, R.array.dusun)
        setupOnItemSelectedListener(
            binding.contentDetailData.contentDataAlamat.spinDusun,
            R.array.dusun
        )

        setupSpinner(
            binding.contentDetailData.contentDataAlamat.spinCaraHubungWarga,
            R.array.cara_hubung_warga
        )
        setupOnItemSelectedListener(
            binding.contentDetailData.contentDataAlamat.spinCaraHubungWarga,
            R.array.cara_hubung_warga
        )

        setupSpinner(
            binding.contentDetailData.contentDataPerkawinan.spinStatusKawin,
            R.array.status_kawin
        )
        setupOnItemSelectedListener(
            binding.contentDetailData.contentDataPerkawinan.spinStatusKawin,
            R.array.status_kawin
        )

        setupSpinner(
            binding.contentDetailData.contentDataKesehatan.spinGolDarah,
            R.array.golongan_darah
        )
        setupOnItemSelectedListener(
            binding.contentDetailData.contentDataKesehatan.spinGolDarah,
            R.array.golongan_darah
        )

        setupSpinner(binding.contentDetailData.contentDataKesehatan.spinCacat, R.array.jenis_cacat)
        setupOnItemSelectedListener(
            binding.contentDetailData.contentDataKesehatan.spinCacat,
            R.array.jenis_cacat
        )

        setupSpinner(
            binding.contentDetailData.contentDataKesehatan.spinSakitMenahun,
            R.array.sakit_menahun
        )
        setupOnItemSelectedListener(
            binding.contentDetailData.contentDataKesehatan.spinSakitMenahun,
            R.array.sakit_menahun
        )

        setupSpinner(binding.contentDetailData.contentDataKesehatan.spinAkseptorKb, R.array.cara_kb)
        setupOnItemSelectedListener(
            binding.contentDetailData.contentDataKesehatan.spinAkseptorKb,
            R.array.cara_kb
        )

        setupSpinner(
            binding.contentDetailData.contentDataKesehatan.spinAsrKesehatan,
            R.array.asuransi
        )
        setupOnItemSelectedListener(
            binding.contentDetailData.contentDataKesehatan.spinAsrKesehatan,
            R.array.asuransi
        )

        setupSpinner(
            binding.contentDetailData.contentDataKesehatan.spinStatusHamil,
            R.array.status_kehamilan
        )
        setupOnItemSelectedListener(
            binding.contentDetailData.contentDataKesehatan.spinStatusHamil,
            R.array.status_kehamilan
        )

        setupSpinner(binding.contentDetailData.contentDataLainnya.spinBacaHuruf, R.array.bahasa)
        setupOnItemSelectedListener(
            binding.contentDetailData.contentDataLainnya.spinBacaHuruf,
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
                binding.contentDetailData.contentDataDiri.tglPenerbitanKtpResult.text =
                    dateFormat.format(calendar.time)
            }

            TANGGAL_LAHIR_DATE_PICKER -> {
                binding.contentDetailData.contentDataLahir.tglLahirResult.text =
                    dateFormat.format(calendar.time)
            }

            EXP_PASPOR_DATE_PICKER -> {
                binding.contentDetailData.contentDataKewarganegaraan.expPasporResult.text =
                    dateFormat.format(calendar.time)
            }

            TANGGAL_KAWIN_DATE_PICKER -> {
                binding.contentDetailData.contentDataPerkawinan.tanggalKawinResult.text =
                    dateFormat.format(calendar.time)
            }

            TANGGAL_CERAI_DATE_PICKER -> {
                binding.contentDetailData.contentDataPerkawinan.tglCeraiResult.text =
                    dateFormat.format(calendar.time)
            }
        }
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        binding.contentDetailData.contentDataLahir.waktuLahirResult.text =
            dateFormat.format(calendar.time)
    }
}
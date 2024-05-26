package com.desabulila.snapcencus.ui.user.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desabulila.snapcencus.data.ResultState
import com.desabulila.snapcencus.data.SnapCencusRepository
import com.desabulila.snapcencus.data.network.response.CommonResponse
import com.desabulila.snapcencus.data.network.response.DetailPendudukResponse
import kotlinx.coroutines.launch

class DetailPendudukViewModel(private val snapCencusRepository: SnapCencusRepository) : ViewModel() {

    private val _detailPendudukResult = MutableLiveData<ResultState<DetailPendudukResponse>>()
    val detailPendudukResult: LiveData<ResultState<DetailPendudukResponse>> = _detailPendudukResult

    private val _updatePendudukResult = MutableLiveData<ResultState<CommonResponse>>()
    val updatePendudukResut: LiveData<ResultState<CommonResponse>> = _updatePendudukResult

    fun getDetailPenduduk(nik: String) {
        viewModelScope.launch {
            snapCencusRepository.getDetailPenduduk(nik).collect {
                _detailPendudukResult.value = it
            }
        }
    }

    fun updatePenduduk(
        nik: String,
        nama: String,
        statusKtp: String,
        statusRekam: String,
        tagIdCard: String,
        tempatCetakKtp: String,
        tanggalCetakKtp: String,
        noKKSebelumnya: String,
        statusDalamKeluarga: String,
        jenisKelamin: String,
        agama: String,
        statusPenduduk: String,
        noAktaLahir: String,
        tempatLahir: String,
        tanggalLahir: String,
        waktuLahir: String,
        tempatDilahirkan: String,
        jenisKelahiran: String,
        kelahiranAnakKe: String,
        penolongKelahiran: String,
        beratLahir: String,
        panjangLahir: String,
        pendidikan: String,
        pendidikanSedang: String,
        pekerjaan: String,
        suku: String,
        wargaNegara: String,
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
        rw: String,
        rt: String,
        alamatSebelumnya: String,
        notelepon: String,
        email: String,
        telegram: String,
        caraHubungWarga: String,
        statusKawin: String,
        noAktaNikah: String,
        tanggalKawin: String,
        noAktaCerai: String,
        tanggalCerai: String,
        golDarah: String,
        cacatId: String,
        sakitMenahun: String,
        caraKb: String,
        idAsuransi: String,
        noAsuransi: String,
        noBpjs: String,
        statusHamil: String,
        bacaHuruf: String,
        keterangan: String
    ) {
        viewModelScope.launch {
            snapCencusRepository.updatePenduduk(
                nik,
                nama,
                statusKtp,
                statusRekam,
                tagIdCard,
                tempatCetakKtp,
                tanggalCetakKtp,
                noKKSebelumnya,
                statusDalamKeluarga,
                jenisKelamin,
                agama,
                statusPenduduk,
                noAktaLahir,
                tempatLahir,
                tanggalLahir,
                waktuLahir,
                tempatDilahirkan,
                jenisKelahiran,
                kelahiranAnakKe,
                penolongKelahiran,
                beratLahir,
                panjangLahir,
                pendidikan,
                pendidikanSedang,
                pekerjaan,
                suku,
                wargaNegara,
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
                notelepon,
                email,
                telegram,
                caraHubungWarga,
                statusKawin,
                noAktaNikah,
                tanggalKawin,
                noAktaCerai,
                tanggalCerai,
                golDarah,
                cacatId,
                sakitMenahun,
                caraKb,
                idAsuransi,
                noAsuransi,
                noBpjs,
                statusHamil,
                bacaHuruf,
                keterangan
            ).collect {
                _updatePendudukResult.value = it
            }
        }
    }
}
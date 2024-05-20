package com.desabulila.snapcencus.ui.user.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desabulila.snapcencus.data.ResultState
import com.desabulila.snapcencus.data.SnapCencusRepository
import com.desabulila.snapcencus.data.network.response.CommonResponse
import com.desabulila.snapcencus.data.network.response.DetailPendudukResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EditDataViewModel(private val snapCencusRepository: SnapCencusRepository) : ViewModel() {

    private val _resultState: MutableStateFlow<ResultState<DetailPendudukResponse?>> =
        MutableStateFlow(ResultState.Loading())
    val resultState: StateFlow<ResultState<DetailPendudukResponse?>> = _resultState

    private val _updatePendudukResultState: MutableStateFlow<ResultState<CommonResponse?>> =
        MutableStateFlow(ResultState.Loading())
    val updatePendudukResultState: StateFlow<ResultState<CommonResponse?>> = _updatePendudukResultState

    fun getDetailPenduduk(nik: String) {
        viewModelScope.launch {
            snapCencusRepository.getDetailPenduduk(nik).collectLatest {
                _resultState.value = it
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
            snapCencusRepository.updateDataPenduduk(
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
            ).collectLatest {
                _updatePendudukResultState.value = it
            }
        }
    }
}
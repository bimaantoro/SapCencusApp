package com.desabulila.snapcencus.data

import com.desabulila.snapcencus.data.model.BaseModel
import com.desabulila.snapcencus.data.network.response.CommonResponse
import com.desabulila.snapcencus.data.network.response.DetailPendudukResponse
import com.desabulila.snapcencus.data.network.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class SnapCencusRepository(
    private val apiService: ApiService
) {

    suspend fun getDataPenduduk(): Flow<Result<DataModel?>> {
        return wrapWithFlow(
            apiService::getData.invoke()
        )
    }

    suspend fun getListPenduduk(): Flow<Result<BaseModel?>> {
        return wrapWithFlow(
            apiService::getPenduduk.invoke()
        )
    }

    suspend fun getDetailPenduduk(nik: String): Flow<Result<DetailPendudukResponse?>> {
        return wrapWithFlow(
            apiService::getDetailPenduduk.invoke(nik)
        )
    }

    suspend fun addNewPenduduk(
        nik: String,
        nama: String,
        sex: String,
        agamaId: String,
        tempatLahir: String,
        tanggalLahir: String,
        pekerjaanId: String,
        wargaNegaraId: String,
        qRt: String,
        qRw: String,
        statusKawin: String,
        golDarahId: String,
        alamat: String,
        idCluster: String,
        qKecamatan: String,
        hubunganKeluarga: String,
        pendidikanKKId: String,
        namaAyah: String,
        namaIbu: String,
        hubungWarga: String
    ): Flow<Result<CommonResponse?>> {
        return wrapWithFlow(
            apiService::createPenduduk.invoke(
                nik,
                nama,
                sex,
                agamaId,
                tempatLahir,
                tanggalLahir,
                pekerjaanId,
                wargaNegaraId,
                qRt,
                qRw,
                statusKawin,
                golDarahId,
                alamat,
                idCluster,
                qKecamatan,
                hubunganKeluarga,
                pendidikanKKId,
                namaAyah,
                namaIbu,
                hubungWarga
            )
        )
    }

    suspend fun updateDataPenduduk(
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
        keterangan: String,
    ): Flow<Result<CommonResponse?>> {
        return wrapWithFlow(
            apiService::updatePenduduk.invoke(
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
            )
        )
    }

    private fun <T> wrapWithFlow(function: Response<T>): Flow<Result<T?>> {
        return flow {
            emit(Result.Loading())
            try {
                if (function.isSuccessful) {
                    emit(Result.Success(function.body()))
                } else {
                    emit(Result.Error(throwable = Throwable(function.message())))
                }
            } catch (e: Exception) {
                emit(Result.Error(throwable = e))
            }
        }
    }


    companion object {
        @Volatile
        private var instance: SnapCencusRepository? = null

        fun getInstance(
            apiService: ApiService
        ): SnapCencusRepository = instance ?: synchronized(this) {
            instance ?: SnapCencusRepository(apiService)
        }.also { instance = it }
    }
}
package com.desabulila.snapcencus.data.repository

import com.desabulila.snapcencus.data.ResultState
import com.desabulila.snapcencus.data.model.ListDataModel
import com.desabulila.snapcencus.data.network.response.CommonResponse
import com.desabulila.snapcencus.data.network.response.DetailPendudukResponse
import com.desabulila.snapcencus.data.network.retrofit.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

class SnapCencusRepository(
    private val apiService: ApiService
) {

    suspend fun getListData(): Flow<ResultState<ListDataModel>> = flow {
        emit(ResultState.Loading())
        try {
            val response = apiService.getBaseData()
            emit(ResultState.Success(response))
        } catch (exc: HttpException) {
            emit(ResultState.Error(exc.message.toString()))
        }
    }.flowOn(Dispatchers.IO)


    suspend fun getPenduduk(): Flow<ResultState<ListDataModel>> = flow {
        emit(ResultState.Loading())
        try {
            val response = apiService.getPenduduk()
            emit(ResultState.Success(response))
        } catch (exc: HttpException) {
            emit(ResultState.Error(exc.message.toString()))
        } catch (exc: Exception) {
            emit(ResultState.Error(exc.message.toString()))
        }
    }.flowOn(Dispatchers.IO)


    suspend fun getDetailPenduduk(nik: String): Flow<ResultState<DetailPendudukResponse>> = flow {
        emit(ResultState.Loading())
        try {
            val response = apiService.getDetailPenduduk(nik)
            emit(ResultState.Success(response))
        } catch (exc: HttpException) {
            emit(ResultState.Error(exc.message.toString()))
        } catch (exc: Exception) {
            emit(ResultState.Error(exc.message.toString()))
        }
    }.flowOn(Dispatchers.IO)


    suspend fun postPenduduk(
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
    ): Flow<ResultState<CommonResponse>> = flow {
        emit(ResultState.Loading())
        try {
            val response = apiService.postPenduduk(
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

            emit(ResultState.Success(response))
        } catch (exc: HttpException) {
            val errorBody = exc.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, CommonResponse::class.java)
            emit(ResultState.Error(errorResponse.pesan))
        } catch (exc: Exception) {
            emit(ResultState.Error(exc.message.toString()))
        }

    }.flowOn(Dispatchers.IO)

    suspend fun updatePenduduk(
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
    ): Flow<ResultState<CommonResponse>> = flow {
        emit(ResultState.Loading())
        try {
            val response = apiService.updatePenduduk(
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
            emit(ResultState.Success(response))
        } catch (exc: HttpException) {
            val errorBody = exc.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, CommonResponse::class.java)
            emit(ResultState.Error(errorResponse.pesan))
        } catch (exc: Exception) {
            emit(ResultState.Error(exc.message.toString()))
        }
    }.flowOn(Dispatchers.IO)


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
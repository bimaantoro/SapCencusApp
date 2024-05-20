package com.desabulila.snapcencus.data.network.retrofit

import com.desabulila.snapcencus.data.model.ListDataModel
import com.desabulila.snapcencus.data.network.response.CommonResponse
import com.desabulila.snapcencus.data.network.response.DetailPendudukResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {


    @GET("get.php?api_key=87Y78GF78SHFDSHFU")
    suspend fun getBaseData(): ListDataModel

    @GET("get_penduduk.php?api_key=87Y78GF78SHFDSHFU")
    suspend fun getPenduduk(): ListDataModel

    @GET("get_penduduk_detail.php?api_key=87Y78GF78SHFDSHFU")
    suspend fun getDetailPenduduk(
        @Query("nik") nik: String
    ): DetailPendudukResponse

    @FormUrlEncoded
    @POST("create_penduduk.php?api_key=87Y78GF78SHFDSHFU")
    suspend fun postPenduduk(
        @Field("nik") ktp: String,
        @Field("nama") nama: String,
        @Field("sex") sex: String,
        @Field("agama_id") agamaId: String,
        @Field("tempatlahir") tempatLahir: String,
        @Field("tanggallahir") tanggalLahir: String,
        @Field("pekerjaan_id") pekerjaanId: String,
        @Field("warganegara_id") warganegaraId: String,
        @Field("qRt") qRt: String,
        @Field("qRw") qRw: String,
        @Field("status_kawin") statusKawin: String,
        @Field("golongan_darah_id") golDarahId: String,
        @Field("alamat") alamat: String,
        @Field("id_cluster") idCluster: String,
        @Field("qKecamatan") qkecamatan: String,
        @Field("hubungan_keluarga") hubunganKeluarga: String,
        @Field("pendidikan_kk_id") pendidikanKKId: String,
        @Field("nama_ayah") namaAyah: String,
        @Field("nama_ibu") namaIbu: String,
        @Field("hubung_warga") hubungWarga: String,
    ): CommonResponse

    @FormUrlEncoded
    @POST("update_penduduk.php?api_key=87Y78GF78SHFDSHFU")
    suspend fun updatePenduduk(
        @Field("nik") nik: String,
        @Field("nama") nama: String,
        @Field("ktp_el") ktpEl: String,
        @Field("status_rekam") statusRekam: String,
        @Field("tag_id_card") tagIdCard: String,
        @Field("tempat_cetak_ktp") tempatCetakKtp: String,
        @Field("tanggal_cetak_ktp") tanggalCetakKtp: String,
        @Field("no_kk_sebelumnya") noKKSebelumnya: String,
        @Field("kk_level") kkLevel: String,
        @Field("id_sex") idSex: String,
        @Field("agama_id") agamaId: String,
        @Field("id_status") idStatus: String,

        //data kelahiran
        @Field("akta_lahir") aktaLahir: String,
        @Field("tempatlahir") tempatLahir: String,
        @Field("tanggallahir") tanggalLahir: String,
        @Field("waktu_lahir") waktuLahir: String,
        @Field("tempat_dilahirkan") tempatDilahirkan: String,
        @Field("jenis_kelahiran") jKelahiran: String,
        @Field("kelahiran_anak_ke") kelahiranAnakKe: String,
        @Field("penolong_kelahiran") penolongKelahiran: String,
        @Field("berat_lahir") beratLahir: String,
        @Field("panjang_lahir") panjangLahir: String,

        //pendidikan dan pekerjaan
        @Field("pendidikan_kk_id") pendidikanKKId: String,
        @Field("pendidikan_sedang_id") pendidikanSedangId: String,
        @Field("pekerjaan_id") pekerjaanId: String,

        //kewarganegaraan
        @Field("suku") suku: String,
        @Field("warganegara_id") kewarnegaraanId: String,
        @Field("dokumen_pasport") dokumenPasport: String,
        @Field("tanggal_akhir_paspor") tglAkhirPassport: String,
        @Field("dokumen_kitas") dokumenKitas: String,
        @Field("negara_asal") negaraAsal: String,

        //Orang tua
        @Field("ayah_nik") ayahNik: String,
        @Field("nama_ayah") namaAyah: String,
        @Field("ibu_nik") ibuNik: String,
        @Field("nama_ibu") namaIbu: String,

        //alamat
        @Field("alamat") alamat: String,
        @Field("dusun") dusun: String,
        @Field("rw") rw: String,
        @Field("id_cluster") idCluster: String,
        @Field("alamat_sebelumnya") alamatSebelumnya: String,
        @Field("telepon") telepon: String,
        @Field("email") email: String,
        @Field("telegram") telegram: String,
        @Field("hubung_warga") narahubung: String,

        //Perkawinan
        @Field("status_kawin") statusKawin: String,
        @Field("akta_perkawinan") aktaPerkawinan: String,
        @Field("tanggalperkawinan") tanggalPerkawinan: String,
        @Field("akta_perceraian") aktaPerceraian: String,
        @Field("tanggalperceraian") tanggalPerceraian: String,

        //kesehatan
        @Field("golongan_darah_id") golDarahId: String,
        @Field("cacat_id") cacatId: String,
        @Field("sakit_menahun_id") sakitMenahunId: String,
        @Field("cara_kb_id") caraKBId: String,
        @Field("id_asuransi") idAsuransi: String,
        @Field("no_asuransi") noAsuransi: String,
        @Field("bpjs_ketenagakerjaan") noBpjs: String,
        @Field("hamil") hamil: String,

        //lainnya
        @Field("bahasa_id") bahasaId: String,
        @Field("ket") ket: String
    ): CommonResponse
}
package com.desabulila.snapcencus.data.model

import com.google.gson.annotations.SerializedName

data class PendudukModel(
    // DATA DIRI
    @field:SerializedName("nik")
    val nik: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("ktp_el")
    val ktpEl: String? = null,

    @field:SerializedName("kk_level")
    val kkLevel: String? = null,

    @field:SerializedName("agama_id")
    val agamaId: String? = null,

    @field:SerializedName("tag_id_card")
    val tagIdCard: String? = null,

    @field:SerializedName("status_rekam")
    val statusRekam: String? = null,

    @field:SerializedName("no_kk_sebelumnya")
    val noKkSebelumnya: String? = null,

    @field:SerializedName("tanggal_cetak_ktp")
    val tanggalCetakKtp: String? = null,

    @field:SerializedName("tempat_cetak_ktp")
    val tempatCetakKtp: String? = null,


    // DATA KELAHIRAN
    @field:SerializedName("akta_lahir")
    val aktaLahir: String? = null,

    @field:SerializedName("tempatlahir")
    val tempatlahir: String? = null,

    @field:SerializedName("tanggallahir")
    val tanggallahir: String? = null,

    @field:SerializedName("waktu_lahir")
    val waktuLahir: String? = null,

    @field:SerializedName("tempat_dilahirkan")
    val tempatDilahirkan: String? = null,

    @field:SerializedName("kelahiran_anak_ke")
    val kelahiranAnakKe: String? = null,

    @field:SerializedName("jenis_kelahiran")
    val jenisKelahiran: String? = null,

    @field:SerializedName("panjang_lahir")
    val panjangLahir: String? = null,

    @field:SerializedName("berat_lahir")
    val beratLahir: String? = null,

    @field:SerializedName("penolong_kelahiran")
    val penolongKelahiran: String? = null,


    // PENDIDIKAN & PEKERJAAN
    @field:SerializedName("pendidikan_kk_id")
    val pendidikanKkId: String? = null,

    @field:SerializedName("pekerjaan_id")
    val pekerjaanId: String? = null,

    @field:SerializedName("pendidikan_sedang_id")
    val pendidikanSedangId: String? = null,

    // KEWARGANEGARAAN
    @field:SerializedName("suku")
    val suku: String? = null,

    @field:SerializedName("warganegara_id")
    val warganegaraId: String? = null,

    @field:SerializedName("dokumen_pasport")
    val dokumenPasport: String? = null,

    @field:SerializedName("dokumen_kitas")
    val dokumenKitas: String? = null,

    @field:SerializedName("negara_asal")
    val negaraAsal: String? = null,

    @field:SerializedName("tanggal_akhir_paspor")
    val tanggalAkhirPaspor: String? = null,

    // DATA ORTU
    @field:SerializedName("ayah_nik")
    val ayahNik: String? = null,

    @field:SerializedName("nama_ibu")
    val namaIbu: String? = null,

    @field:SerializedName("ibu_nik")
    val ibuNik: String? = null,

    @field:SerializedName("nama_ayah")
    val namaAyah: String? = null,

    // ALAMAT
    @field:SerializedName("alamat_sekarang")
    val alamatSekarang: String? = null,

    @field:SerializedName("alamat_sebelumnya")
    val alamatSebelumnya: String? = null,

    @field:SerializedName("id_cluster")
    val idCluster: String? = null,

    // KONTAK
    @field:SerializedName("telepon")
    val telepon: String? = null,

    @field:SerializedName("telegram")
    val telegram: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("hubung_warga")
    val hubungWarga: String? = null,

    // PERKAWINAN
    @field:SerializedName("status_kawin")
    val statusKawin: String? = null,

    @field:SerializedName("tanggalperkawinan")
    val tanggalperkawinan: String? = null,

    @field:SerializedName("akta_perkawinan")
    val aktaPerkawinan: String? = null,

    @field:SerializedName("tanggalperceraian")
    val tanggalperceraian: String? = null,

    @field:SerializedName("akta_perceraian")
    val aktaPerceraian: String? = null,

    // KESEHATAN
    @field:SerializedName("golongan_darah_id")
    val golonganDarahId: String? = null,

    @field:SerializedName("cacat_id")
    val cacatId: String? = null,

    @field:SerializedName("sakit_menahun_id")
    val sakitMenahunId: String? = null,

    @field:SerializedName("cara_kb_id")
    val caraKbId: String? = null,

    @field:SerializedName("id_asuransi")
    val idAsuransi: String? = null,

    @field:SerializedName("no_asuransi")
    val noAsuransi: String? = null,

    @field:SerializedName("bpjs_ketenagakerjaan")
    val bpjsKetenagakerjaan: String? = null,

    @field:SerializedName("hamil")
    val hamil: String? = null,

    // LAINNYA
    @field:SerializedName("bahasa_id")
    val bahasaId: String? = null,

    @field:SerializedName("ket")
    val ket: String? = null,
)
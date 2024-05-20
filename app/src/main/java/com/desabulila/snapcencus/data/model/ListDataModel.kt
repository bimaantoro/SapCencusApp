package com.desabulila.snapcencus.data.model

import com.google.gson.annotations.SerializedName

data class ListDataModel(
    @field:SerializedName("dusun")
    val dusun: List<DusunModel> = emptyList(),

    @field:SerializedName("pekerjaan")
    val pekerjaan: List<PekerjaanModel> = emptyList(),

    @field:SerializedName("golongan_darah")
    val golonganDarah: List<GolonganDarahModel> = emptyList(),

    @field:SerializedName("agama")
    val agama: List<AgamaModel> = emptyList(),

    @field:SerializedName("dataPenduduk")
    val dataPenduduk: List<PendudukModel> = emptyList(),

    @field:SerializedName("jenisKelamin")

    val jenisKelamin: List<JenisKelaminModel> = emptyList(),

    @field:SerializedName("statusKawin")
    val statusKawin: List<StatusKawinModel> = emptyList()
)
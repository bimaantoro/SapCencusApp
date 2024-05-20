package com.desabulila.snapcencus.data.model

import com.google.gson.annotations.SerializedName

data class DusunModel(
    @field:SerializedName("rt")
    val rt: String? = null,
    @field:SerializedName("rw")
    val rw: String? = null,
    @field:SerializedName("id")
    val id: String? = null,
    @field:SerializedName("dusun")
    val dusun: String? = null,
    @field:SerializedName("id_kepala")
    val idKepala: String? = null,
)
package com.desabulila.sapcencus.data.network.response

import com.google.gson.annotations.SerializedName

data class CommonResponse(

    @field:SerializedName("pesan")
    val pesan: String,

    @field:SerializedName("kode")
    val kode: Int
)

package com.desabulila.snapcencus.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class KtpModel(
    val nik: String? = null,
    val nama: String? = null,
    val tempatLahir: String? = null,
    val tanggalLahir: String? = null,
    val jenisKelamin: String? = null,
    val golDarah: String? = null,
    val alamat: String? = null,
    val rt: String? = null,
    val rw: String? = null,
    val kel: String? = null,
    val kec: String? = null,
    val agama: String? = null,
    val statusKawin: String? = null,
    val pekerjaan: String? = null,
    val statusWargaNegara: String? = null,
) : Parcelable
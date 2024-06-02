package com.desabulila.snapcencus.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class KtpModel(
    var nik: String? = null,
    var nama: String? = null,
    var tempatLahir: String? = null,
    var tanggalLahir: String? = null,
    var jenisKelamin: String? = null,
    var golDarah: String? = null,
    var alamat: String? = null,
    var rtRw: String? = null,
    var rw: String? = null,
    var kel: String? = null,
    var kec: String? = null,
    var agama: String? = null,
    var statusKawin: String? = null,
    var pekerjaan: String? = null,
    var statusWargaNegara: String? = null,
) : Parcelable
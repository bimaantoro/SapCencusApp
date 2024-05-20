package com.desabulila.snapcencus.data.model

data class KKModel(
    var hubunganKeluarga: String? = null, // KEPALA KELUARGA, ANAK, ISTRI, MENANTU, CUCU, ORANG TUA, MERTUA, FAMILI LAIN, PEMBANTU, LAINNYA
    var pendidikan: String? = null,  // TIDAK / BELUM SEKOLAH, BELUM TAMAT SD/SEDERAJAT, TAMAT SD / SEDERAJAT, SLTP/SEDERAJAT, SLTA / SEDERAJAT, DIPLOMA I / II, AKADEMI / DIPLOMA III / S. MUDA, DIPLOMA IV / STRATA I, STRATA II, STRATA III
    var namaAyah: String? = null,
    var namaIbu: String? = null,
    var caraHubungWarga: String? = null //email, telegram
)
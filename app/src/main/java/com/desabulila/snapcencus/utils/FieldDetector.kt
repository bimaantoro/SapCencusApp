package com.desabulila.snapcencus.utils

import android.graphics.Rect

object FieldDetector {
    fun checkNikField(data: String) = data.lowercase().trim() == "nik"

    fun checkNamaField(dataText: String): Boolean {
        val text = dataText.lowercase().trim()
        return StringConstant.FIELD_NAMA.contains(text)
    }

    fun checkTglLahirField(dataText: String): Boolean {
        val text = dataText.lowercase().trim()
        return StringConstant.FIELD_TEMPAT_TGL_LAHIR.contains(text)
    }

    fun checkAlamatField(dataText: String): Boolean {
        val text = dataText.lowercase().trim()
        return StringConstant.FIELD_ALAMAT.contains(text)
    }

    fun checkRtRwField(dataText: String): Boolean {
        val text = dataText.lowercase().trim()
        return StringConstant.FIELD_RT_RW.contains(text)
    }

    fun checkKelDesaField(dataText: String): Boolean {
        val text = dataText.lowercase().trim()
        return StringConstant.FIELD_KEL_DESA.contains(text)
    }

    fun checkKecamatanField(dataText: String): Boolean {
        val text = dataText.lowercase().trim()
        return StringConstant.FIELD_KECAMATAN.contains(text)
    }

    fun isInside(rect: Rect?, isInside: Rect?): Boolean {
        if (rect == null || isInside == null) {
            return false
        }
        return rect.centerY() <= isInside.bottom &&
                rect.centerY() >= isInside.top &&
                rect.centerX() >= isInside.left &&
                rect.centerX() <= isInside.right
    }

    fun isInside3Rect(isThisRect: Rect?, isInside: Rect?, andAbove: Rect?): Boolean {
        if (isThisRect == null || isInside == null || andAbove == null) {
            return false
        }
        return isThisRect.centerY() <= andAbove.top &&
                isThisRect.centerY() >= isInside.top &&
                isThisRect.centerX() >= isInside.left
    }

}
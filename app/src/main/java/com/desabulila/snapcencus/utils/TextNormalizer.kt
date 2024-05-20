package com.desabulila.snapcencus.utils

object TextNormalizer {
    fun normalizeNikText(text: String): String {
        var result = text.uppercase()
        result = result
            .replace("NIK", "")
            .replace(":", "")
            .trim()
        return result
    }

    fun normalizeNamaText(text: String): String? {
        var result = text.uppercase()

        for (element in StringConstant.FIELD_NAMA) {
            result = result.replace(element.uppercase(), "")
        }

        result = result
            .replace("NIK", "")
            .replace("-", "")
            .replace(":", "")
            .replace("1", "I")
            .replace("\\s+", " ")
            .trim()

        result = fixAsciiCharacters(result)

        return result.ifEmpty { null }
    }

    fun normalizeAlamatText(text: String): String {
        var result = text.uppercase()

        for (element in StringConstant.FIELD_ALAMAT) {
            result = result.replace(element.uppercase(), "")
        }

        result = result.replace("RI/KEILDESAA", "")
            .replace("RTKELIIDESAA", "")
            .replace("TIKEL/LDESA", "")
            .replace(":", "")
            .replace("=", "")
            .replace("\\s+", " ")
            .trim()

        result = fixAsciiCharacters(result)
        return result
    }

    fun normalizeRtRwText(text: String): String? {
        var result = text.uppercase()

        for (element in StringConstant.FIELD_RT_RW) {
            result = result.replace(element.uppercase(), "")
        }

        result = result
            .replace("-", "")
            .replace(":", "")
            .replace("=", "")
            .replace("\\s+", " ")
            .replace("O", "0")
            .trim()

        result = addSlashEveryThreeDigits(result)!!
        return if (result == "") null else result
    }

    fun normalizeDesaKelText(text: String): String {
        var result = text.uppercase()

        for (element in StringConstant.FIELD_KEL_DESA) {
            result = result.replace(element.uppercase(), "")
        }

        result = result
            .replace("-", "")
            .replace(":", "")
            .replace("=", "")
            .replace("\\s+", " ")
            .trim()

        result = fixAsciiCharacters(result)
        return result
    }

    fun normalizeKecamatanText(text: String): String {
        var result = text.uppercase()

        for (element in StringConstant.FIELD_KECAMATAN) {
            result = result.replace(element.uppercase(), "")
        }

        result = result
            .replace(":", "")
            .replace("-", "")
            .replace("=", "")
            .replace("\\s+", " ")
            .trim()

        result = fixAsciiCharacters(result)
        return result
    }

    private fun addSlashEveryThreeDigits(text: String): String? {
        val result = StringBuilder()
        var count = 0

        if (!text.contains("/")) {
            for (i in text.indices) {
                val c = text[i]
                result.append(c)

                if (Character.isDigit(c)) {
                    count++

                    if (count == 3 && i < text.length - 1 && text[i + 1] != '/') {
                        result.append("/")
                        count = 0
                    }
                } else if (c == '/') {
                    count = 0
                }
            }
        } else {
            return text
        }

        if (result.indexOf("/") == 0) {
            return null
        }

        return result.toString()
    }

    private fun fixAsciiCharacters(text: String): String {
        return text
            .replace("Ä", "A")
            .replace("Ü", "U")
            .replace("ü", "u")
            .replace("Ö", "O")
            .replace("ö", "o")
            .replace("Ñ", "N")
            .replace("Ë", "E")
            .replace("ë", "e")
            .replace("ÿ", "y")
            .replace("ï", "i")
    }
}
package com.example.petabencana.utils

object AreaList {

    val dataList = arrayListOf(
        "Aceh",
        "Bali",
        "Kep Bangka Belitung",
        "Banten",
        "Bengkulu",
        "Jawa Tengah",
        "Kalimantan Tengah",
        "Sulawesi Tengah",
        "Jawa Timur",
        "Kalimantan Timur",
        "Nusa Tenggara Timur",
        "Gorontalo",
        "DKI Jakarta",
        "Jambi",
        "Lampung",
        "Maluku",
        "Kalimantan Utara",
        "Maluku Utara",
        "Sulawesi Utara",
        "Sumatra Utara",
        "Papua",
        "Riau",
        "Kepulauan Riau",
        "Sulawesi Tenggara",
        "Kalimantan Selatan",
        "Sulawesi Selatan",
        "Sumatra Selatan",
        "DI Yogyakarta",
        "Jawa Barat",
        "Kalimantan Barat",
        "Nusa Tenggara Barat",
        "Papua Barat",
        "Sulawesi Barat",
        "Sumatra Barat",
    )

    fun getAreaId(provinsi: String):String {
        return when (provinsi) {
            "Aceh" -> "ID-AC"

            "Bali" -> "ID-BA"

            "Kep Bangka Belitung" -> "ID-BB"

            "Banten" -> "ID-BT"

            "Bengkulu" -> "ID-BE"

            "Jawa Tengah" -> "ID-JT"

            "Kalimantan Tengah" -> "ID-KT"

            "Sulawesi Tengah" -> "ID-ST"

            "Jawa Timur" -> "ID-JI"

            "Kalimantan Timur" -> "ID-KI"

            "Nusa Tenggara Timur" -> "ID-NT"

            "Gorontalo" -> "ID-GO"

            "DKI Jakarta" -> "ID-JK"

            "Jambi" ->"ID-JA"

            "Lampung" ->"ID-LA"

            "Maluku" -> "ID-MA"

            "Kalimantan Utara" -> "ID-KU"

            "Maluku Utara" -> "ID-MU"

            "Sulawesi Utara" -> "ID-SA"

            "Sumatra Utara" ->"ID-SU"

            "Papua" -> "ID-PA"

            "Riau" -> "ID-RI"

            "Kepulauan Riau" -> "ID-KR"

            "Sulawesi Tenggara" ->"ID-SG"

            "Kalimantan Selatan" ->"ID-KS"

            "Sulawesi Selatan" -> "ID-SN"

            "Sumatra Selatan" -> "ID-SS"

            "DI Yogyakarta" -> "ID-YO"

            "Jawa Barat" -> "ID-JB"

            "Kalimantan Barat" -> "ID-KB"

            "Nusa Tenggara Barat" -> "ID-NB"

            "Papua Barat" -> "ID-PB"

            "Sulawesi Barat" -> "ID-SR"

            "Sumatra Barat" -> "ID-SB"

            else -> ""
        }
    }

}
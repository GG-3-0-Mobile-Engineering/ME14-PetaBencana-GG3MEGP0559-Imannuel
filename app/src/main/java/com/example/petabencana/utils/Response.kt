package com.example.petabencana.utils

import androidx.room.TypeConverter
import com.example.petabencana.data.model.ReportData
import com.example.petabencana.data.model.Tags
import com.google.gson.Gson

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromReportData(reportData: ReportData?): String? {
        return gson.toJson(reportData)
    }

    @TypeConverter
    fun toReportData(json: String?): ReportData? {
        return gson.fromJson(json, ReportData::class.java)
    }

    @TypeConverter
    fun fromTags(tags: Tags?): String? {
        return gson.toJson(tags)
    }

    @TypeConverter
    fun toTags(json: String?): Tags? {
        return gson.fromJson(json, Tags::class.java)
    }
}

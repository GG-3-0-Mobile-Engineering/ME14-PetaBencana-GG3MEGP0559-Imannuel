package com.example.petabencana.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken


data class UrunDaya(
    @SerializedName("statusCode") var statusCode: Int? = null,

    @SerializedName("result") var result: Result? = null
)

data class ReportData(
    @SerializedName("report_type")
    val report_type: String? = null,

    @SerializedName("flood_depth")
    val flood_depth: Int? = null
)

data class Tags(
    @SerializedName("district_id")
    val district_id: String? = null,

    @SerializedName("region_code")
    val region_code: String? = null,

    @SerializedName("local_area_id")
    val local_area_id: String? = null,

    @SerializedName("instance_region_code")
    val instance_region_code: String? = null
)

@Entity(tableName = "urundaya")
data class Properties(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @SerializedName("pkey") var pkey: String? = null,

    @ColumnInfo
    @SerializedName("created_at") var createdAt: String? = null,

    @ColumnInfo
    @SerializedName("source") var source: String? = null,

    @ColumnInfo
    @SerializedName("status") var status: String? = null,

    @ColumnInfo
    @SerializedName("url") var url: String? = null,

    @ColumnInfo
    @SerializedName("image_url") var imageUrl: String? = null,

    @ColumnInfo
    @SerializedName("disaster_type") var disasterType: String? = null,

    @ColumnInfo
    @SerializedName("report_data") var reportData: ReportData? = null,

    @ColumnInfo
    @SerializedName("tags") var tags: Tags? = null,

    @ColumnInfo
    @SerializedName("title") var title: String? = null,

    @ColumnInfo
    @SerializedName("text") var text: String? = null
)

data class Geometries(
    @SerializedName("type") var type: String? = null,

    @SerializedName("properties") var properties: Properties? = null,

    @SerializedName("coordinates") var coordinates: List<Double> = emptyList()
)

data class Output(
    @SerializedName("type") var type: String? = null,

    @SerializedName("geometries") var geometries: List<Geometries> = emptyList()
)

data class Objects(
    @SerializedName("output") var output: Output? = null
)

data class Transform(
    @SerializedName("scale") var scale: String? = null,

    @SerializedName("translate") var translate: ArrayList<Double> = arrayListOf()
)

data class Result(
    @SerializedName("type") var type: String? = null,

    @SerializedName("objects") var objects: Objects? = null,

    @SerializedName("arcs") var arcs: ArrayList<String> = arrayListOf(),

    @SerializedName("transform") var transform: Transform? = null,

    @SerializedName("bbox") var bbox: ArrayList<Double> = arrayListOf()
)




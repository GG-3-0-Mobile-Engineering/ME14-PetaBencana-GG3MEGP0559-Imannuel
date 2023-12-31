package com.imannuel.petabencana.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


data class UrunDaya(
    @SerializedName("statusCode") var statusCode: Int? = null,

    @SerializedName("result") var result: Result? = null
)

data class ReportData(
    @SerializedName("report_type")
    val report_type: String? = null,

    //flood
    @SerializedName("flood_depth")
    val flood_depth: Int? = null,

    //volcano
    @SerializedName("volcanicSigns")
    val volcanicSigns: ArrayList<Int>? = null,

    @SerializedName("evacuationNumber")
    val evacuationNumber: Int? = null,

    @SerializedName("evacuationArea")
    val evacuationArea: Boolean? = null,

    //earthquake
    @SerializedName("accessabilityFailure")
    val accessabilityFailure: Int? = null,

    @SerializedName("structureFailure")
    val structureFailure: Int? = null,

    @SerializedName("condition")
    val condition: Int? = null,
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

@Parcelize
@Entity(tableName = "urundaya")
data class Properties(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @SerializedName("pkey") var pkey: String? = null,

    @ColumnInfo
    var isSaved: Boolean = false,

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
    @SerializedName("report_data") var reportData: @RawValue ReportData? = null,

    @ColumnInfo
    @SerializedName("tags") var tags: @RawValue Tags? = null,

    @ColumnInfo
    @SerializedName("title") var title: String? = null,

    @ColumnInfo
    @SerializedName("text") var text: String? = null
) : Parcelable

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




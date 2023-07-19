package com.example.petabencana.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class UrunDaya(
    @SerializedName("statusCode") var statusCode: Int? = null,
    @SerializedName("result") var result: Result? = Result()
)

data class Tags(
    @SerializedName("instance_region_code") var instanceRegionCode: String? = null,
    @SerializedName("local_area_id") var localAreaId: String? = null
)

@Entity(tableName = "urundaya")
data class Properties(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("pkey") var pkey: Int = 0,

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
    @SerializedName("report_data") var reportData: String? = null,
//    @SerializedName("tags"          ) var tags         : Tags?   = Tags(),

    @ColumnInfo
    @SerializedName("title") var title: String? = null,

    @ColumnInfo
    @SerializedName("text") var text: String? = null
)

data class Geometries(
    @SerializedName("type") var type: String? = null,
    @SerializedName("properties") var properties: Properties? = Properties(),
    @SerializedName("coordinates") var coordinates: ArrayList<Int> = arrayListOf()
)

data class Output(
    @SerializedName("type") var type: String? = null,
    @SerializedName("geometries") var geometries: ArrayList<Geometries> = arrayListOf()
)

data class Objects(
    @SerializedName("output") var output: Output? = Output()
)

data class Transform(
    @SerializedName("scale") var scale: String? = null,
    @SerializedName("translate") var translate: ArrayList<Double> = arrayListOf()
)

data class Result(
    @SerializedName("type") var type: String? = null,
    @SerializedName("objects") var objects: Objects? = Objects(),
    @SerializedName("arcs") var arcs: ArrayList<String> = arrayListOf(),
    @SerializedName("transform") var transform: Transform? = Transform(),
    @SerializedName("bbox") var bbox: ArrayList<Double> = arrayListOf()
)
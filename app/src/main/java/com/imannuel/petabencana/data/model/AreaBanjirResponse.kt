package com.imannuel.petabencana.data.model

import com.google.gson.annotations.SerializedName

data class AreaBanjir(

    @SerializedName("statusCode") var statusCode: Int? = null,
    @SerializedName("result") var result: AreaBanjirResult? = AreaBanjirResult()

)

data class AreaBanjirProperties(

    @SerializedName("area_id") var areaId: String? = null,
    @SerializedName("geom_id") var geomId: String? = null,
    @SerializedName("area_name") var areaName: String? = null,
    @SerializedName("parent_name") var parentName: String? = null,
    @SerializedName("city_name") var cityName: String? = null,
    @SerializedName("state") var state: Int = 1,
    @SerializedName("last_updated") var lastUpdated: String? = null

)

data class AreaBanjirGeometries(

    @SerializedName("type") var type: String? = null,
    @SerializedName("properties") var properties: AreaBanjirProperties? = AreaBanjirProperties(),
    @SerializedName("arcs") var arcs: ArrayList<ArrayList<Int>> = arrayListOf()

)

data class AreaBanjirOutput(

    @SerializedName("type") var type: String? = null,
    @SerializedName("geometries") var geometries: List<AreaBanjirGeometries> = emptyList()

)

data class AreaBanjirObjects(

    @SerializedName("output") var output: AreaBanjirOutput? = AreaBanjirOutput()

)

data class AreaBanjirTransform(

    @SerializedName("scale") var scale: ArrayList<Double> = arrayListOf(),
    @SerializedName("translate") var translate: ArrayList<Double> = arrayListOf()

)

data class AreaBanjirResult(

    @SerializedName("type") var type: String? = null,
    @SerializedName("objects") var objects: AreaBanjirObjects? = AreaBanjirObjects(),
    @SerializedName("arcs") var arcs: ArrayList<ArrayList<ArrayList<Int>>> = arrayListOf(),
    @SerializedName("transform") var transform: AreaBanjirTransform? = AreaBanjirTransform(),
    @SerializedName("bbox") var bbox: ArrayList<Double> = arrayListOf()

)
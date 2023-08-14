package com.imannuel.petabencana

import com.imannuel.petabencana.data.model.*

object AreaBanjirDummyData {
    val dummyAreaBanjirProperties = AreaBanjirProperties(
        areaId = "1",
        geomId = "geom1",
        areaName = "Area 1",
        parentName = "Parent 1",
        cityName = "City 1",
        state = 1,
        lastUpdated = "2023-08-13"
    )

    val dummyAreaBanjirGeometries = AreaBanjirGeometries(
        type = "Point",
        properties = dummyAreaBanjirProperties,
        arcs = arrayListOf(
            arrayListOf(1, 2, 3),
            arrayListOf(4, 5, 6)
        )
    )

    val dummyAreaBanjirOutput = AreaBanjirOutput(
        type = "FeatureCollection",
        geometries = listOf(dummyAreaBanjirGeometries)
    )

    val dummyAreaBanjirObjects = AreaBanjirObjects(
        output = dummyAreaBanjirOutput
    )

    val dummyAreaBanjirTransform = AreaBanjirTransform(
        scale = arrayListOf(1.0, 2.0),
        translate = arrayListOf(3.0, 4.0)
    )

    val dummyAreaBanjirResult = AreaBanjirResult(
        type = "Topology",
        objects = dummyAreaBanjirObjects,
        arcs = arrayListOf(
            arrayListOf(
                arrayListOf(1, 2, 3),
                arrayListOf(4, 5, 6)
            ),
            arrayListOf(
                arrayListOf(7, 8, 9),
                arrayListOf(10, 11, 12)
            )
        ),
        transform = dummyAreaBanjirTransform,
        bbox = arrayListOf(0.0, 0.0, 100.0, 100.0)
    )

    val dummyAreaBanjir = AreaBanjir(
        statusCode = 200,
        result = dummyAreaBanjirResult
    )
}
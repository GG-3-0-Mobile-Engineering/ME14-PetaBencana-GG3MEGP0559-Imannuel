package com.imannuel.petabencana

import com.imannuel.petabencana.data.model.*

object UrunDayaDummyData {

    val dummyReportData = ReportData(
        report_type = "flood",
        flood_depth = 50,
        volcanicSigns = arrayListOf(1, 2, 3),
        evacuationNumber = 100,
        evacuationArea = true,
        accessabilityFailure = 5,
        structureFailure = 3,
        condition = 2
    )

    val dummyTags = Tags(
        district_id = "123",
        region_code = "ABC",
        local_area_id = "456",
        instance_region_code = "XYZ"
    )

    val dummyProperties = Properties(
        pkey = "dummy_key",
        isSaved = true,
        createdAt = "2023-08-11",
        source = "sample_source",
        status = "published",
        url = "https://example.com",
        imageUrl = "https://example.com/image.jpg",
        disasterType = "flood",
        reportData = dummyReportData,
        tags = dummyTags,
        title = "Sample Title",
        text = "Lorem ipsum dolor sit amet."
    )

    val dummyGeometries = Geometries(
        type = "Point",
        properties = dummyProperties,
        coordinates = listOf(123.456, 789.012)
    )

    val dummyOutput = Output(
        type = "FeatureCollection",
        geometries = listOf(dummyGeometries)
    )

    val dummyObjects = Objects(
        output = dummyOutput
    )

    val dummyTransform = Transform(
        scale = "scale_value",
        translate = arrayListOf(1.0, 2.0)
    )

    val dummyResult = Result(
        type = "Topology",
        objects = dummyObjects,
        arcs = arrayListOf("arc1", "arc2"),
        transform = dummyTransform,
        bbox = arrayListOf(0.0, 0.0, 100.0, 100.0)
    )

    val dummyUrunDaya = UrunDaya(
        statusCode = 200,
        result = dummyResult
    )


}
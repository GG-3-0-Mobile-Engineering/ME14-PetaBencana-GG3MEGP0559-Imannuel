package com.imannuel.petabencana

import com.imannuel.petabencana.data.model.Properties
import com.imannuel.petabencana.data.model.ReportData
import com.imannuel.petabencana.data.model.Tags

object PropertiesDummyData {

    val sampleTags = Tags(
        district_id = "district_id",
        region_code = "region_code",
        local_area_id = "local_area_id",
        instance_region_code = "instance_region_code"
    )

    val sampleReportData = ReportData(
        report_type = "Flood",
        flood_depth = 50,
        volcanicSigns = arrayListOf(1, 2, 3),
        evacuationNumber = 5,
        evacuationArea = true,
        accessabilityFailure = 3,
        structureFailure = 2,
        condition = 1
    )

    val sampleProperties = Properties(
        uid = 1,
        pkey = "pkey",
        isSaved = false,
        createdAt = "createdAt",
        source = "source",
        status = "status",
        url = "url",
        imageUrl = "imageUrl",
        disasterType = "earthquake",
        reportData = sampleReportData,
        tags = sampleTags,
        title = "title",
        text = "text"
    )


}
package com.imannuel.petabencana.utils

import org.junit.Assert.*
import org.junit.Test

class TimeUtilsTest{

    @Test
    fun `test getTimeAgo for seconds`() {
        val dateTimeString = "2023-08-12T10:30:00.000Z"

        val result = TimeUtils.getTimeAgo(dateTimeString)

        assertEquals("2 days ago", result)
    }

    @Test
    fun `test getTimeAgo for invalid date`() {
        val dateTimeString = ""

        val result = TimeUtils.getTimeAgo(dateTimeString)

        assertEquals("", result)
    }

}
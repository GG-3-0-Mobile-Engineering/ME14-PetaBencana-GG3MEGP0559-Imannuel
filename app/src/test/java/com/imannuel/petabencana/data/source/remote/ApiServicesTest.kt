package com.imannuel.petabencana.data.source.remote

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.imannuel.petabencana.AreaBanjirDummyData
import com.imannuel.petabencana.UrunDayaDummyData
import com.imannuel.petabencana.data.source.remote.ApiServices
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response


@ExperimentalCoroutinesApi
class ApiServicesTest {

    @Mock
    private lateinit var apiServices: ApiServices

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testGetLatestReports() = runBlocking {
        val admin = "admin"
        val disaster = "disaster"
        val timePeriod = 1
        val urunDayaResponse = UrunDayaDummyData.dummyUrunDaya

        Mockito.`when`(
            apiServices.getLatestReports(admin, disaster, timePeriod)
        ).thenReturn(urunDayaResponse)

        val response = apiServices.getLatestReports(admin, disaster, timePeriod)

        assertEquals(urunDayaResponse, response)
    }

    @Test
    fun testGetRealTimeFlood() = runBlocking {
        val admin = "admin"
        val minimumState = 1
        val areaBanjirResponse = AreaBanjirDummyData.dummyAreaBanjir

        Mockito.`when`(
            apiServices.getRealTimeFlood(minimumState, admin)
        ).thenReturn(areaBanjirResponse)

        val response = apiServices.getRealTimeFlood(minimumState, admin)

        assertEquals(areaBanjirResponse, response)
    }


}

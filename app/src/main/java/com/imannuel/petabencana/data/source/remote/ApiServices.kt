package com.imannuel.petabencana.data.source.remote

import com.imannuel.petabencana.data.model.AreaBanjir
import com.imannuel.petabencana.data.model.UrunDaya
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiServices {

    @GET("reports")
    suspend fun getLatestReports(
        @Query("admin") admin: String? = null,
        @Query("disaster") disaster: String? = null,
        @Query("timeperiod") timePeriod: Int? = null
    ): UrunDaya

    @GET("floods")
    suspend fun getRealTimeFlood(
        @Query("minimum_state") minimumState: Int = 1,
        @Query("admin") admin: String = "ID-JK",
    ): AreaBanjir
}
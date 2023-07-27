package com.imannuel.petabencana.data.repository

import com.imannuel.petabencana.data.model.AreaBanjir
import com.imannuel.petabencana.data.source.remote.ApiServices
import com.imannuel.petabencana.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AreaBanjirRepository(private val apiServices: ApiServices) {

    suspend fun getAreaBanjir(): Flow<Resource<AreaBanjir>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.getRealTimeFlood()
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.toString()))
            }
        }
    }
}
package com.example.petabencana.data.repository

import com.example.petabencana.data.model.Properties
import com.example.petabencana.data.model.UrunDaya
import com.example.petabencana.data.source.local.room.UrunDayaDAO
import com.example.petabencana.data.source.remote.ApiServices
import com.example.petabencana.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UrunDayaRepository(private val apiServices: ApiServices, private val urunDayaDAO: UrunDayaDAO) {

    fun getUrunDaya() : Flow<Resource<UrunDaya>>{
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.getLatestReports()
                emit(Resource.Success(response))
            }catch (e:Exception){
                emit(Resource.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getAllSavedUrunDaya() = urunDayaDAO.getAllSavedUrunDaya()

    fun getSavedUrunDayaById(id:Int) = urunDayaDAO.getSavedUrunDayaById(id)

    suspend fun saveUrunDaya(urunDaya: Properties) = urunDayaDAO.saveUrunDaya(urunDaya)

    suspend fun deleteUrunDaya(urunDaya: Properties) = urunDayaDAO.deleteUrunDaya(urunDaya)

}
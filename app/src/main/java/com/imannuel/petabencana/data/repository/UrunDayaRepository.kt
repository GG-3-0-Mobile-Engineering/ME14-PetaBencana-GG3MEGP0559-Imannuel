package com.imannuel.petabencana.data.repository

import com.imannuel.petabencana.data.model.Properties
import com.imannuel.petabencana.data.model.UrunDaya
import com.imannuel.petabencana.data.source.local.room.UrunDayaDAO
import com.imannuel.petabencana.data.source.remote.ApiServices
import com.imannuel.petabencana.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UrunDayaRepository @Inject constructor(
    private val apiServices: ApiServices,
    private val urunDayaDAO: UrunDayaDAO
) {

    //api
    suspend fun getUrunDaya(
        admin: String? = null,
        disaster: String? = null,
        timePeriod: Int? = null
    ): Flow<Resource<UrunDaya>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.getLatestReports(
                    admin = admin?.takeIf { !it.isNullOrEmpty() },
                    disaster = disaster?.takeIf { !it.isNullOrEmpty() },
                    timePeriod = timePeriod?.takeIf { it != 0 }
                )

                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.toString()))
            }
        }
    }

    //db
    fun getAllSavedUrunDaya() = urunDayaDAO.getAllSavedUrunDaya()

    fun isUrunDayaExist(id: String) = urunDayaDAO.isUrunDayaExist(id)

    suspend fun saveUrunDaya(urunDaya: Properties) = urunDayaDAO.saveUrunDaya(urunDaya)

    suspend fun deleteUrunDaya(id: String) = urunDayaDAO.deleteUrunDaya(id)

}
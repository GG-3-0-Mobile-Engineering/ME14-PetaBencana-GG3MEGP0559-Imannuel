package com.imannuel.petabencana.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.imannuel.petabencana.data.model.Properties


@Dao
interface UrunDayaDAO {

    @Query("SELECT * from urundaya")
    fun getAllSavedUrunDaya(): LiveData<List<Properties>>

    @Query("SELECT * from urundaya WHERE pkey = :id")
    fun getSavedUrunDayaById(id: Int): LiveData<Properties>

    @Insert
    suspend fun saveUrunDaya(urunDaya: Properties): Long

    @Delete
    suspend fun deleteUrunDaya(urunDaya: Properties)

}
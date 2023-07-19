package com.example.petabencana.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.petabencana.data.model.Properties
import com.example.petabencana.data.model.UrunDaya


@Dao
interface UrunDayaDAO {

    @Query("SELECT * from urundaya")
    fun getAllSavedUrunDaya() : LiveData<List<Properties>>

    @Query("SELECT * from urundaya WHERE pkey = :id")
    fun getSavedUrunDayaById(id: Int) : LiveData<Properties>

    @Insert
    suspend fun saveUrunDaya(urunDaya: Properties) : Long

    @Delete
    suspend fun deleteUrunDaya(urunDaya: Properties)

}
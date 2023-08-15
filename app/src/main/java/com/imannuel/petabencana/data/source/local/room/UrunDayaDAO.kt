package com.imannuel.petabencana.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.imannuel.petabencana.data.model.Properties


@Dao
interface UrunDayaDAO {

    @Query("SELECT * from urundaya")
    fun getAllSavedUrunDaya(): LiveData<List<Properties>>

    @Query("SELECT EXISTS(SELECT * from urundaya WHERE pkey = :id)")
    fun isUrunDayaExist(id: String): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUrunDaya(urunDaya: Properties): Long

    @Query("DELETE FROM URUNDAYA WHERE pkey =:id")
    suspend fun deleteUrunDaya(id: String)

}
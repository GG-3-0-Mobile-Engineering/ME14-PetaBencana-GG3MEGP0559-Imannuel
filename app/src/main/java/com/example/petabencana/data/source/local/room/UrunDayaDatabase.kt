package com.example.petabencana.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.petabencana.data.model.Properties


@Database(entities = [Properties::class], version = 1, exportSchema = false)
abstract class UrunDayaDatabase : RoomDatabase(){
    abstract fun urunDayaDao() : UrunDayaDAO
}
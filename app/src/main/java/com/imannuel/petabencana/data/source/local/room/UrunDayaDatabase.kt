package com.imannuel.petabencana.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.imannuel.petabencana.utils.Converters
import com.imannuel.petabencana.data.model.Properties


@Database(entities = [Properties::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class UrunDayaDatabase : RoomDatabase() {
    abstract fun urunDayaDao(): UrunDayaDAO
}
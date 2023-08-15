package com.imannuel.petabencana.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.imannuel.petabencana.data.model.Properties
import com.imannuel.petabencana.utils.Converters


@Database(entities = [Properties::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class UrunDayaDatabase : RoomDatabase() {

    abstract fun urunDayaDao(): UrunDayaDAO

    companion object{
        @Volatile private var instance: UrunDayaDatabase? = null

        fun getDatabase(context: Context): UrunDayaDatabase = instance ?: synchronized(this){
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, UrunDayaDatabase::class.java, "UrunDayaDB")
                .build()

    }
}
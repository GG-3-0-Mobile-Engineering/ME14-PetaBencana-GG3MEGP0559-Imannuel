package com.imannuel.petabencana.di

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.startup.Initializer
import androidx.work.Configuration
import androidx.work.WorkManager
import com.imannuel.petabencana.BuildConfig
import com.imannuel.petabencana.data.repository.AreaBanjirRepository
import com.imannuel.petabencana.data.repository.UrunDayaRepository
import com.imannuel.petabencana.data.source.local.room.UrunDayaDAO
import com.imannuel.petabencana.data.source.local.room.UrunDayaDatabase
import com.imannuel.petabencana.data.source.remote.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        UrunDayaDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun provideUrunDayaDAO(database: UrunDayaDatabase): UrunDayaDAO {
        return database.urunDayaDao()
    }

    @Singleton
    @Provides
    fun provideAreaBanjirRepository(apiServices: ApiServices) = AreaBanjirRepository(apiServices)

    @Singleton
    @Provides
    fun provideUrunDayaRepository(
        apiServices: ApiServices,
        urunDayaDAO: UrunDayaDAO
    ) = UrunDayaRepository(apiServices, urunDayaDAO)



}

@Module
@InstallIn(SingletonComponent::class)
object WorkManagerInitializer: Initializer<WorkManager> {
    @Provides
    @Singleton
    override fun create(@ApplicationContext context: Context): WorkManager {
        val entryPoint = EntryPointAccessors.fromApplication(
            context,
            WorkManagerInitializerEntryPoint::class.java
        )
        val configuration = Configuration
            .Builder()
            .setWorkerFactory(entryPoint.hiltWorkerFactory())
            .setMinimumLoggingLevel(if (BuildConfig.DEBUG) Log.DEBUG else Log.INFO)
            .build()

        WorkManager.initialize(context, configuration)
        return WorkManager.getInstance(context)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }

    @InstallIn(SingletonComponent::class)
    @EntryPoint
    interface WorkManagerInitializerEntryPoint {
        fun hiltWorkerFactory(): HiltWorkerFactory
    }
}




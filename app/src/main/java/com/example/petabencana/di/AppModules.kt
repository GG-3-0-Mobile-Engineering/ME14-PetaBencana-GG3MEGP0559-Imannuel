package com.example.petabencana.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.petabencana.data.model.Properties
import com.example.petabencana.data.repository.UrunDayaRepository
import com.example.petabencana.data.source.local.preference.UserPreferences
import com.example.petabencana.data.source.local.room.UrunDayaDAO
import com.example.petabencana.data.source.local.room.UrunDayaDatabase
import com.example.petabencana.data.source.remote.ApiConfig
import com.example.petabencana.ui.home.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

val dbModule = module {
    single { Room.databaseBuilder(androidApplication(), UrunDayaDatabase::class.java, "UrunDayaDB").build() }

    single { get<UrunDayaDatabase>().urunDayaDao() }
}

val apiModule = module {
    single { ApiConfig(androidContext().dataStore).getApiService() }
}

val prefModule = module {
    single { UserPreferences(androidContext().dataStore) }
}

val repositoryModule = module {
    single { UrunDayaRepository(get(), get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
}
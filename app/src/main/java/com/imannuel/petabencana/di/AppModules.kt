package com.imannuel.petabencana.di

import androidx.room.Room
import com.imannuel.petabencana.data.repository.AreaBanjirRepository
import com.imannuel.petabencana.data.repository.UrunDayaRepository
import com.imannuel.petabencana.data.source.local.room.UrunDayaDatabase
import com.imannuel.petabencana.data.source.remote.ApiConfig
import com.imannuel.petabencana.ui.home.HomeViewModel
import com.imannuel.petabencana.ui.saved.SavedViewModel
import com.imannuel.petabencana.ui.saved.detail.SavedDetailViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val dbModule = module {
    single {
        Room.databaseBuilder(androidApplication(), UrunDayaDatabase::class.java, "UrunDayaDB")
            .build()
    }

    single { get<UrunDayaDatabase>().urunDayaDao() }
}

val apiModule = module {
    single { ApiConfig().getApiService() }
}

val repositoryModule = module {
    single { UrunDayaRepository(get(), get()) }
    single { AreaBanjirRepository(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { SavedViewModel(get()) }
    viewModel { SavedDetailViewModel(get()) }
}
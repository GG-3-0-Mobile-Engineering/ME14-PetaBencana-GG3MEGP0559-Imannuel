package com.imannuel.petabencana.di

import android.content.Context
import com.imannuel.petabencana.data.repository.AreaBanjirRepository
import com.imannuel.petabencana.data.repository.UrunDayaRepository
import com.imannuel.petabencana.data.source.local.room.UrunDayaDAO
import com.imannuel.petabencana.data.source.local.room.UrunDayaDatabase
import com.imannuel.petabencana.data.source.remote.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = UrunDayaDatabase.getDatabase(context)

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


//val dbModule = module {
//    single {
//        Room.databaseBuilder(androidApplication(), UrunDayaDatabase::class.java, "UrunDayaDB")
//            .build()
//    }
//
//    single { get<UrunDayaDatabase>().urunDayaDao() }
//}
//
//val apiModule = module {
//    single { ApiConfig().getApiService() }
//}
//
//val repositoryModule = module {
//    single { UrunDayaRepository(get(), get()) }
//    single { AreaBanjirRepository(get()) }
//}
//
//val viewModelModule = module {
//    viewModel { HomeViewModel(get()) }
//    viewModel { SavedViewModel(get()) }
//    viewModel { SavedDetailViewModel(get()) }
//}
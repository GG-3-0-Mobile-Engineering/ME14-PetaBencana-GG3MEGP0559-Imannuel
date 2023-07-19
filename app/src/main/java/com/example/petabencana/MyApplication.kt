package com.example.petabencana

import android.app.Application
import com.example.petabencana.di.apiModule
import com.example.petabencana.di.dbModule
import com.example.petabencana.di.prefModule
import com.example.petabencana.di.repositoryModule
import com.example.petabencana.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@MyApplication)
            modules(
                dbModule,
                apiModule,
                prefModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}
package com.example.petabencana

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
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

        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        preferences.getString("darkModePreferenceKey", "auto").apply {
            val nightMode = when (this) {
                "auto" -> -1
                "off" -> 1
                "on" -> 2
                else -> -1
            }
            AppCompatDelegate.setDefaultNightMode(nightMode)
        }

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
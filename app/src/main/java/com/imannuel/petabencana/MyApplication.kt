package com.imannuel.petabencana

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.imannuel.petabencana.di.apiModule
import com.imannuel.petabencana.di.dbModule
import com.imannuel.petabencana.di.repositoryModule
import com.imannuel.petabencana.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
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

        startKoin {
            androidContext(this@MyApplication)
            modules(
                dbModule,
                apiModule,
                repositoryModule,
                viewModelModule
            )
        }
    }


}
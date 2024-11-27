package com.example.leaflove

import android.app.Application
import android.util.Log
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.leaflove.data.database.AppDatabase
import com.example.leaflove.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Start Koin
        startKoin {
            androidContext(this@MyApp) // Provide the application context
            modules(appModule) // Load your Koin modules
        }
    }

}
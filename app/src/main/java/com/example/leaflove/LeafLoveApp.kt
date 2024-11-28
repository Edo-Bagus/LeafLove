package com.example.leaflove

import android.app.Application
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cloudinary.android.MediaManager
import com.example.leaflove.data.database.AppDatabase
import com.example.leaflove.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val config = mutableMapOf(
            "cloud_name" to "ddrnhafts",  // Replace with your cloud name
            "api_key" to "751861626526443", // Replace with your API key
            "api_secret" to "2daW9bFOlzqNJPqZCLAgkU4OSSg" // Replace with your API secret
        )
        MediaManager.init(this, config)

        // Start Koin
        startKoin {
            androidContext(this@MyApp) // Provide the application context
            modules(appModule) // Load your Koin modules
        }
    }

}
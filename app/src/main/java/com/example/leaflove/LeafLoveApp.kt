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

const val cloudName = BuildConfig.CLOUD_NAME
const val cloudApiKey = BuildConfig.CLOUDINARY_API_KEY
const val cloudApiSecret = BuildConfig.CLOUDINARY_API_SECRET

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val config = mutableMapOf(
            "cloud_name" to cloudName,  // Replace with your cloud name
            "api_key" to cloudApiKey, // Replace with your API key
            "api_secret" to cloudApiSecret // Replace with your API secret
        )
        MediaManager.init(this, config)

        // Start Koin
        startKoin {
            androidContext(this@MyApp) // Provide the application context
            modules(appModule) // Load your Koin modules
        }
    }

}
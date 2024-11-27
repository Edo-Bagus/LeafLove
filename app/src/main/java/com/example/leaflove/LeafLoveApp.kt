package com.example.leaflove

import android.app.Application
import android.util.Log
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.leaflove.data.database.AppDatabase

class MyApp : Application() {
    companion object {
        private lateinit var instance: MyApp
        fun getDatabase(): AppDatabase {
            return instance.database
        }
    }

    private lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        instance = this // Initialize the singleton instance
        // Initialize the Room database here
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "plant-database"
        ).build()
    }

    // Provide a method to access the DAO
    fun getPlantSpeciesDao() = database.plantSpeciesDao()
}
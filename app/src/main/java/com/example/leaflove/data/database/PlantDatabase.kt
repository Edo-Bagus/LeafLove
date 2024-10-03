package com.example.leaflove.data.database

import com.example.leaflove.data.converters.Converters
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.leaflove.data.dao.PlantSpeciesDao
import com.example.leaflove.data.entities.PlantSpeciesEntity


@Database(entities = [PlantSpeciesEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun plantSpeciesDao(): PlantSpeciesDao
}
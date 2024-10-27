package com.example.leaflove.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.leaflove.data.entities.PlantSpeciesEntity

@Dao
interface PlantSpeciesDao {

    @Insert
    suspend fun insertAll(plantSpecies: List<PlantSpeciesEntity>)

    @Query("SELECT * FROM plant_species")
    suspend fun getAllPlants(): List<PlantSpeciesEntity>

    @Query("SELECT (SELECT COUNT (*) FROM plant_species) == 0")
    suspend fun checkIsEmpty(): Boolean
}

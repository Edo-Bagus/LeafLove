package com.example.leaflove.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.leaflove.data.entities.PlantDetailEntity
import com.example.leaflove.data.entities.PlantSpeciesEntity

@Dao
interface PlantDetailDao {

    // Insert a new PlantDetailEntity and return its new ID
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlantDetail(plantDetail: PlantDetailEntity): Long

    // Update the detailId in the PlantSpeciesEntity
    @Query("UPDATE plant_species SET detailId = :newDetailId WHERE id = :speciesId")
    suspend fun updatePlantSpeciesWithDetailId(speciesId: Int, newDetailId: Int)

    @Query("SELECT (SELECT COUNT (*) FROM plant_detail) == 0")
    suspend fun checkIsEmpty(): Boolean

    @Query("SELECT (SELECT COUNT (*) FROM plant_detail WHERE id = :speciesId ) == 0")
    suspend fun checkIsFilled(speciesId: Int): Boolean

    @Query("SELECT * FROM plant_detail WHERE id = :speciesId")
    suspend fun getPlantDetail(speciesId: Int):PlantDetailEntity
}

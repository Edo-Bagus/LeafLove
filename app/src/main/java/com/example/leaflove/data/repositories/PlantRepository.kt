package com.example.leaflove.data.repositories

import com.example.leaflove.data.dao.PlantDetailDao
import com.example.leaflove.data.dao.PlantSpeciesDao
import com.example.leaflove.data.entities.PlantDetailEntity
import com.example.leaflove.data.entities.PlantSpeciesEntity

class PlantRepository(private val plantSpeciesDao: PlantSpeciesDao, private val plantDetailDao: PlantDetailDao) {

    suspend fun insertPlants(plants: List<PlantSpeciesEntity>) {
        plantSpeciesDao.insertAll(plants)
    }

    suspend fun getAllPlants(): List<PlantSpeciesEntity> {
        return plantSpeciesDao.getAllPlants()
    }

    suspend fun insertPlantDetailAndLinkToSpecies(
        plantDetail: PlantDetailEntity,
        speciesId: Int,
        plantDao: PlantDetailDao
    ) {
        // Insert the new PlantDetailEntity and get its new ID
        val newDetailId = plantDao.insertPlantDetail(plantDetail).toInt()

        // Update the PlantSpeciesEntity with the new detailId
        plantDao.updatePlantSpeciesWithDetailId(speciesId, newDetailId)
    }
}

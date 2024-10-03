package com.example.leaflove.data.repositories

import com.example.leaflove.data.dao.PlantSpeciesDao
import com.example.leaflove.data.entities.PlantSpeciesEntity

class PlantRepository(private val plantSpeciesDao: PlantSpeciesDao) {

    suspend fun insertPlants(plants: List<PlantSpeciesEntity>) {
        plantSpeciesDao.insertAll(plants)
    }

    suspend fun getAllPlants(): List<PlantSpeciesEntity> {
        return plantSpeciesDao.getAllPlants()
    }
}

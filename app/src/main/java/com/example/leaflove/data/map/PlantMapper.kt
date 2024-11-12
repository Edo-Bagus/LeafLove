// In a new file: PlantMapper.kt
package com.example.leaflove.data.map

import com.example.leaflove.data.entities.PlantSpeciesEntity
import com.example.leaflove.data.models.DefaultImage
import com.example.leaflove.data.models.PlantSpecies
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object PlantMapper {

    fun plantToEntity(plant: PlantSpecies): PlantSpeciesEntity {
        return PlantSpeciesEntity(
            id = plant.id,
            common_name = plant.common_name,
            scientific_name = Gson().toJson(plant.scientific_name),
            other_name = Gson().toJson(plant.other_name),
            cycle = plant.cycle,
            watering = plant.watering,
            sunlight = Gson().toJson(plant.sunlight),
            default_image = Gson().toJson(plant.default_image),
            detailId = null
        )
    }

    fun plantToEntityList(plants: List<PlantSpecies>): List<PlantSpeciesEntity>{
        var plantList:List<PlantSpeciesEntity> = emptyList();
        for(plant in plants){
            plantList += PlantSpeciesEntity(
                id = plant.id,
                common_name = plant.common_name,
                scientific_name = Gson().toJson(plant.scientific_name),
                other_name = Gson().toJson(plant.other_name),
                cycle = plant.cycle,
                watering = plant.watering,
                sunlight = Gson().toJson(plant.sunlight),
                default_image = Gson().toJson(plant.default_image),
                detailId = null
            )
        }
        return plantList;
    }

    fun entityToPlant(plantEntity: PlantSpeciesEntity): PlantSpecies {
        return PlantSpecies(
            id = plantEntity.id,
            common_name = plantEntity.common_name,
            scientific_name = Gson().fromJson(plantEntity.scientific_name, object : TypeToken<List<String>>() {}.type),
            other_name = Gson().fromJson(plantEntity.other_name, object : TypeToken<List<String>?>() {}.type),
            cycle = plantEntity.cycle,
            watering = plantEntity.watering,
            sunlight = Gson().fromJson(plantEntity.sunlight, object : TypeToken<List<String>>() {}.type),
            default_image = Gson().fromJson(plantEntity.default_image, DefaultImage::class.java)
        )
    }
}

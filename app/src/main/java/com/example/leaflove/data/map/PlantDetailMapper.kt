package com.example.leaflove.data.map

import com.example.leaflove.data.entities.PlantDetailEntity
import com.example.leaflove.data.models.PlantDetailResponseModel
import com.google.gson.Gson

object PlantDetailMapper {
    fun plantDetailToEntity(plantDetail: PlantDetailResponseModel): PlantDetailEntity {
        return PlantDetailEntity(
            id = plantDetail.id,
            common_name = plantDetail.common_name,
            scientific_name = Gson().toJson(plantDetail.scientific_name),
            other_name = Gson().toJson(plantDetail.other_name),
            family = plantDetail.family,
            origin = Gson().toJson(plantDetail.origin),
            type = plantDetail.type,
            dimension = plantDetail.dimension,
            dimensions = Gson().toJson(plantDetail.dimensions),
            cycle = plantDetail.cycle,
            attracts = Gson().toJson(plantDetail.attracts),
            propagation = Gson().toJson(plantDetail.propagation),
            hardiness = Gson().toJson(plantDetail.hardiness),
            hardiness_location = Gson().toJson(plantDetail.hardiness_location),
            watering = plantDetail.watering,
            depth_water_requirement = Gson().toJson(plantDetail.depth_water_requirement),
            volume_water_requirement = Gson().toJson(plantDetail.volume_water_requirement),
            watering_period = plantDetail.watering_period,
            watering_general_benchmark = Gson().toJson(plantDetail.watering_general_benchmark),
            plant_anatomy = Gson().toJson(plantDetail.plant_anatomy),
            sunlight = Gson().toJson(plantDetail.sunlight),
            pruning_month = Gson().toJson(plantDetail.pruning_month),
            pruning_count = Gson().toJson(plantDetail.pruning_count),
            seeds = plantDetail.seeds,
            maintenance = plantDetail.maintenance,
            care_guides = plantDetail.care_guides,
            soil = Gson().toJson(plantDetail.soil),
            growth_rate = plantDetail.growth_rate,
            drought_tolerant = plantDetail.drought_tolerant,
            salt_tolerant = plantDetail.salt_tolerant,
            thorny = plantDetail.thorny,
            invasive = plantDetail.invasive,
            tropical = plantDetail.tropical,
            indoor = plantDetail.indoor,
            care_level = plantDetail.care_level,
            pest_susceptibility = Gson().toJson(plantDetail.pest_susceptibility),
            pest_susceptibility_api = plantDetail.pest_susceptibility_api,
            flowers = plantDetail.flowers,
            flowering_season = plantDetail.flowering_season,
            flower_color = plantDetail.flower_color,
            cones = plantDetail.cones,
            fruits = plantDetail.fruits,
            edible_fruit = plantDetail.edible_fruit,
            edible_fruit_taste_profile = plantDetail.edible_fruit_taste_profile,
            fruit_nutritional_value = plantDetail.fruit_nutritional_value,
            fruit_color = Gson().toJson(plantDetail.fruit_color),
            harvest_season = plantDetail.harvest_season,
            leaf = plantDetail.leaf,
            leaf_color = Gson().toJson(plantDetail.leaf_color),
            edible_leaf = plantDetail.edible_leaf,
            cuisine = plantDetail.cuisine,
            medicinal = plantDetail.medicinal,
            poisonous_to_humans = plantDetail.poisonous_to_humans,
            poisonous_to_pets = plantDetail.poisonous_to_pets,
            description = plantDetail.description,
            default_image = Gson().toJson(plantDetail.default_image),
            other_images = plantDetail.other_images
        )
    }
}
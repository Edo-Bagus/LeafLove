package com.example.leaflove.data.entities

import com.example.leaflove.data.converters.Converters
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "plant_detail")
@TypeConverters(Converters::class)  // Use the converters to handle complex types
data class PlantDetailEntity(
    @PrimaryKey val id: Int? = null,
    val common_name: String? = null,
    val scientific_name: String? = null,  // Store List<String> as JSON string
    val other_name: String? = null,       // Store List<String> as JSON string
    val family: String? = null,
    val origin: String? = null,           // Store List<String> as JSON string
    val type: String? = null,
    val dimension: String? = null,
    val dimensions: String? = null,       // Store Dimensions object as JSON string
    val cycle: String? = null,
    val attracts: String? = null,         // Store List<String> as JSON string
    val propagation: String? = null,      // Store List<String> as JSON string
    val hardiness: String? = null,        // Store Hardiness object as JSON string
    val hardiness_location: String? = null, // Store HardinessLocation object as JSON string
    val watering: String? = null,
    val depth_water_requirement: String? = null, // Store DepthWaterRequirement as JSON string
    val volume_water_requirement: String? = null, // Store List<String> as JSON string
    val watering_period: String? = null,
    val watering_general_benchmark: String? = null, // Store WateringBenchmark object as JSON string
    val plant_anatomy: String? = null,    // Store List<String> as JSON string
    val sunlight: String? = null,         // Store List<String> as JSON string
    val pruning_month: String? = null,    // Store List<String> as JSON string
    val pruning_count: String? = null,    // Store List<String> as JSON string
    val seeds: Int? = null,
    val maintenance: String? = null,
    val care_guides: String? = null,
    val soil: String? = null,             // Store List<String> as JSON string
    val growth_rate: String? = null,
    val drought_tolerant: Boolean? = null,
    val salt_tolerant: Boolean? = null,
    val thorny: Boolean? = null,
    val invasive: Boolean? = null,
    val tropical: Boolean? = null,
    val indoor: Boolean? = null,
    val care_level: String? = null,
    val pest_susceptibility: String? = null, // Store List<String> as JSON string
    val pest_susceptibility_api: String? = null,
    val flowers: Boolean? = null,
    val flowering_season: String? = null,
    val flower_color: String? = null,
    val cones: Boolean? = null,
    val fruits: Boolean? = null,
    val edible_fruit: Boolean? = null,
    val edible_fruit_taste_profile: String? = null,
    val fruit_nutritional_value: String? = null,
    val fruit_color: String? = null,      // Store List<String> as JSON string
    val harvest_season: String? = null,
    val leaf: Boolean? = null,
    val leaf_color: String? = null,       // Store List<String> as JSON string
    val edible_leaf: Boolean? = null,
    val cuisine: Boolean? = null,
    val medicinal: Boolean? = null,
    val poisonous_to_humans: Int? = null,
    val poisonous_to_pets: Int? = null,
    val description: String? = null,
    val default_image: String? = null,    // Store DefaultImage object as JSON string
    val other_images: String? = null
)
package com.example.leaflove.data.models

data class PlantDetailResponseModel(
    val id: Int = 0,
    val common_name: String = "",
    val scientific_name: List<String> = emptyList(),
    val other_name: List<String> = emptyList(),
    val family: String? = null,
    val origin: List<String> = emptyList(),
    val type: String = "",
    val dimension: String = "",
    val dimensions: Dimensions = Dimensions(),
    val cycle: String = "",
    val attracts: List<String> = emptyList(),
    val propagation: List<String> = emptyList(),
    val hardiness: Hardiness = Hardiness(),
    val hardiness_location: HardinessLocation = HardinessLocation(),
    val watering: String = "",
    val depth_water_requirement: Any = DepthWaterRequirement(),
    val volume_water_requirement: Any = VolumeWaterRequirement(),
    val watering_period: String? = null,
    val watering_general_benchmark: WateringBenchmark = WateringBenchmark(),
    val plant_anatomy: List<PlantAnatomy> = emptyList(),
    val sunlight: List<String> = emptyList(),
    val pruning_month: List<String> = emptyList(),
    val pruning_count: Any? = null,
    val seeds: Int = 0,
    val maintenance: String? = null,
    val care_guides: String = "",
    val soil: List<String> = emptyList(),
    val growth_rate: String = "",
    val drought_tolerant: Boolean = false,
    val salt_tolerant: Boolean = false,
    val thorny: Boolean = false,
    val invasive: Boolean = false,
    val tropical: Boolean = false,
    val indoor: Boolean = false,
    val care_level: String = "",
    val pest_susceptibility: List<String> = emptyList(),
    val pest_susceptibility_api: String = "",
    val flowers: Boolean = false,
    val flowering_season: String? = null,
    val flower_color: String = "",
    val cones: Boolean = false,
    val fruits: Boolean = false,
    val edible_fruit: Boolean = false,
    val edible_fruit_taste_profile: String = "",
    val fruit_nutritional_value: String = "",
    val fruit_color: List<String> = emptyList(),
    val harvest_season: String? = null,
    val leaf: Boolean = false,
    val leaf_color: List<String> = emptyList(),
    val edible_leaf: Boolean = false,
    val cuisine: Boolean = false,
    val medicinal: Boolean = false,
    val poisonous_to_humans: Int = 0,
    val poisonous_to_pets: Int = 0,
    val description: String = "",
    val default_image: DefaultImage = DefaultImage(),
    val other_images: String = ""
)

data class Dimensions(
    val type: String = "",
    val min_value: Int = 0,
    val max_value: Int = 0,
    val unit: String = ""
)

data class Hardiness(
    val min: String = "",
    val max: String = ""
)

data class HardinessLocation(
    val full_url: String = "",
    val full_iframe: String = ""
)

data class DepthWaterRequirement(
    val unit: String = "",
    val value: String = ""
)

data class VolumeWaterRequirement(
    val unit: String = "",
    val value: String = ""
)

data class WateringBenchmark(
    val value: String = "",
    val unit: String = ""
)

data class PlantAnatomy(
    val part: String,
    val color: List<String>,
)

data class PruningCount(
    val amount: Int,
    val interval: String
)
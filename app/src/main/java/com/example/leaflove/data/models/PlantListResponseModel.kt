package com.example.leaflove.data.models

data class PlantListResponseModel (
    val data: List<PlantSpecies>? = null,
    val to: Int? = null,
    val per_page: Int? = null,
    val current_page: Int? = null,
    val from: Int? = null,
    val last_page: Int? = null,
    val total: Int? = null
)

data class PlantSpecies (
    val id: Int,
    val common_name: String,
    val scientific_name: List<String>,
    val other_name: List<String>?,  // Nullable because it can be null
    val cycle: String,
    val watering: String,
    val sunlight: List<String>,     // Assuming this is a list of strings (empty in the example)
    val default_image: DefaultImage // Represents the image object
)

data class DefaultImage(
    val image_id: Int,
    val license: Int,
    val license_name: String,
    val license_url: String,
    val original_url: String,
    val regular_url: String,
    val medium_url: String,
    val small_url: String,
    val thumbnail: String
)
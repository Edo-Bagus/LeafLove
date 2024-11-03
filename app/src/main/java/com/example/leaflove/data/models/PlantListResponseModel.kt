package com.example.leaflove.data.models

data class PlantListResponseModel(
    val data: List<PlantSpecies>? = null,
    val to: Int? = null,
    val per_page: Int? = null,
    val current_page: Int? = null,
    val from: Int? = null,
    val last_page: Int? = null,
    val total: Int? = null
)

data class PlantSpecies(
    val id: Int = 0,
    val common_name: String? = null,
    val scientific_name: List<String>? = null,
    val other_name: List<String>? = null,  // Nullable because it can be null
    val cycle: String? = null,
    val watering: String? = null,
    val sunlight: List<String>? = null,     // Assuming this is a list of strings (empty in the example)
    val default_image: DefaultImage? = null // Represents the image object
)

data class DefaultImage(
    val license: Int? = null,
    val license_name: String? = null,
    val license_url: String? = null,
    val original_url: String? = null,
    val regular_url: String? = null,
    val medium_url: String? = null,
    val small_url: String? = "https://www.svgrepo.com/show/505400/image-1.svg",
    val thumbnail: String? = null
)
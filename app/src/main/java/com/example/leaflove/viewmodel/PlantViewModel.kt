package com.example.leaflove.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leaflove.data.models.DefaultImage
import com.example.leaflove.data.models.PlantListResponseModel
import com.example.leaflove.data.models.PlantSpecies
import com.example.leaflove.services.PerenualAPIService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlantViewModel: ViewModel() {
    private val plantServices: PerenualAPIService = PerenualAPIService.create()

    private val _plantState = mutableStateOf(PlantListResponseModel())
    val plantState = _plantState

    fun fetchPlantList() {
        viewModelScope.launch {
            try {
                val response = plantServices.getPlantList()
                _plantState.value = response
            } catch (e: Exception) {
                e.message?.let { Log.e("Plant Error", it) }
            }
        }
    }
    // Function to fetch plant list (now with dummy data)
    fun fetchPlantListDummy() {
        viewModelScope.launch {
            try {
                // Simulate a network delay
                delay(1000L)

                // Create dummy plant data
                val dummyPlants = listOf(
                    PlantSpecies(
                        id = 3,
                        common_name = "Bonsai",
                        scientific_name = listOf("Rosa"),
                        other_name = listOf("Flowering Rose"),
                        cycle = "Perennial",
                        watering = "Moderate",
                        sunlight = listOf("Full Sun", "Partial Shade"),
                        default_image = DefaultImage(
                            image_id = 1,
                            license = 123,
                            license_name = "LicenseName",
                            license_url = "https://licenseurl.com",
                            original_url = "https://example.com/rose.jpg",
                            regular_url = "https://example.com/rose_regular.jpg",
                            medium_url = "https://example.com/rose_medium.jpg",
                            small_url = "https://example.com/rose_small.jpg",
                            thumbnail = "https://example.com/rose_thumbnail.jpg"
                        )
                    ),
                    PlantSpecies(
                        id = 4,
                        common_name = "Pea",
                        scientific_name = listOf("Helianthus"),
                        other_name = null,
                        cycle = "Annual",
                        watering = "Frequent",
                        sunlight = listOf("Full Sun"),
                        default_image = DefaultImage(
                            image_id = 2,
                            license = 456,
                            license_name = "LicenseName",
                            license_url = "https://licenseurl.com",
                            original_url = "https://example.com/sunflower.jpg",
                            regular_url = "https://example.com/sunflower_regular.jpg",
                            medium_url = "https://example.com/sunflower_medium.jpg",
                            small_url = "https://example.com/sunflower_small.jpg",
                            thumbnail = "https://example.com/sunflower_thumbnail.jpg"
                        )
                    )
                )

                // Set the dummy data as the state
                _plantState.value = PlantListResponseModel(
                    data = dummyPlants,
                    to = 2,
                    per_page = 2,
                    current_page = 1,
                    from = 1,
                    last_page = 1,
                    total = 2
                )
            } catch (e: Exception) {
                e.message?.let { Log.e("Plant Error", it) }
            }
        }
    }
}
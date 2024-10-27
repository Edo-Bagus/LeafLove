package com.example.leaflove.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leaflove.MyApp
import com.example.leaflove.data.entities.PlantSpeciesEntity
import com.example.leaflove.data.models.DefaultImage
import com.example.leaflove.data.models.DepthWaterRequirement
import com.example.leaflove.data.models.Dimensions
import com.example.leaflove.data.models.Hardiness
import com.example.leaflove.data.models.HardinessLocation
import com.example.leaflove.data.models.PlantDetailResponseModel
import com.example.leaflove.data.models.PlantListResponseModel
import com.example.leaflove.data.models.PlantSpecies
import com.example.leaflove.data.models.WateringBenchmark
import com.example.leaflove.data.repositories.PlantRepository
import com.example.leaflove.services.PerenualAPIService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



class PlantViewModel: ViewModel() {

    val plantSpeciesDao = MyApp.getDatabase().plantSpeciesDao()
    val plantDetailDao = MyApp.getDatabase().plantDetailDao()
    private var plantRepository = PlantRepository(plantSpeciesDao, plantDetailDao)

    private val plantServices: PerenualAPIService = PerenualAPIService.create()

    private val _plantState = mutableStateOf(PlantListResponseModel())
    val plantState = _plantState


    private val _plantList = mutableStateOf<List<PlantSpeciesEntity>>(emptyList())
    val plantList = _plantList

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

    fun fetchPlantListFromRoom() {
        viewModelScope.launch {
            try {
                val plantsFromRoom = plantRepository.getAllPlants()
                _plantList.value = plantsFromRoom
            } catch (e: Exception) {
                e.message?.let { Log.e("Plant Error", it) }
            }
        }
    }

    fun insertPlantToRoom(plantEntities: List<PlantSpeciesEntity>){
        viewModelScope.launch {
            try {
                plantRepository.insertPlants(plantEntities)
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
                        id = 1,
                        common_name = "Golden Fullmoon Maple",
                        scientific_name = listOf("Acer japonicum 'Aureum'"),
                        other_name = emptyList(),
                        cycle = "Perennial",
                        watering = "Average",
                        sunlight = listOf("full sun", "part shade"),
                        default_image = DefaultImage(
                            license = 45,
                            license_name = "Attribution-ShareAlike 3.0 Unported (CC BY-SA 3.0)",
                            license_url = "https://creativecommons.org/licenses/by-sa/3.0/deed.en",
                            original_url = "https://perenual.com/storage/species_image/20_acer_japonicum_aureum/og/2560px-Acer_shirasawanum_27Aureum27.jpg",
                            regular_url = "https://perenual.com/storage/species_image/20_acer_japonicum_aureum/regular/2560px-Acer_shirasawanum_27Aureum27.jpg",
                            medium_url = "https://perenual.com/storage/species_image/20_acer_japonicum_aureum/medium/2560px-Acer_shirasawanum_27Aureum27.jpg",
                            small_url = "https://perenual.com/storage/species_image/20_acer_japonicum_aureum/small/2560px-Acer_shirasawanum_27Aureum27.jpg",
                            thumbnail = "https://perenual.com/storage/species_image/20_acer_japonicum_aureum/thumbnail/2560px-Acer_shirasawanum_27Aureum27.jpg"
                        )
                    ),
                    PlantSpecies(
                        id = 2,
                        common_name = "Pea",
                        scientific_name = listOf("Helianthus"),
                        other_name = null,
                        cycle = "Annual",
                        watering = "Frequent",
                        sunlight = listOf("Full Sun"),
                        default_image = DefaultImage(
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
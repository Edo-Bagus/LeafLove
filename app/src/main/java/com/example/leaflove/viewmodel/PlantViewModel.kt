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

    private val _plantDetail = mutableStateOf(PlantDetailResponseModel())
    val plantDetail = _plantDetail

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

    fun fetchPlantDetail(id: Int){
        viewModelScope.launch {
            try {
//                Log.d("halo", "ceg")
                val response = plantServices.getPlantDetail(plantId = id)
//                Log.d("tes", response.toString())
                _plantDetail.value = response
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

    fun fetchPlantDetailDummy() {
        viewModelScope.launch {
            try {
                // Simulate a network delay
                delay(1000L)

                // Create dummy plant detail data
                val dummyPlantDetail = PlantDetailResponseModel(
                    id = 1,
                    common_name = "Golden Fullmoon Maple",
                    scientific_name = listOf("Acer japonicum 'Aureum'"),
                    other_name = emptyList(),
                    family = null,
                    origin = listOf("Japan"),
                    type = "tree",
                    dimension = "Height: 20 feet",
                    dimensions = Dimensions(
                        type = "Height",
                        min_value = 20,
                        max_value = 20,
                        unit = "feet"
                    ),
                    cycle = "Perennial",
                    attracts = emptyList(),
                    propagation = listOf(
                        "Air Layering Propagation",
                        "Grafting Propagation",
                        "Cutting",
                        "Division",
                        "Seed Propagation"
                    ),
                    hardiness = Hardiness(
                        min = "6",
                        max = "6"
                    ),
                    hardiness_location = HardinessLocation(
                        full_url = "https://perenual.com/api/hardiness-map?species_id=20&size=og&key=sk-1ky066d169c2847a46668",
                        full_iframe = "<iframe frameborder=0 scrolling=yes seamless=seamless width=1000 height=550 style='margin:auto;' src='https://perenual.com/api/hardiness-map?species_id=20&size=og&key=sk-1ky066d169c2847a46668'></iframe>"
                    ),
                    watering = "Average",
                    depth_water_requirement = DepthWaterRequirement(
                        unit = "inches",
                        value = "2"
                    ),
                    volume_water_requirement = emptyList(),
                    watering_period = null,
                    watering_general_benchmark = WateringBenchmark(
                        value = "7-10",
                        unit = "days"
                    ),
                    plant_anatomy = emptyList(),
                    sunlight = listOf("full sun", "part shade"),
                    pruning_month = listOf(
                        "February", "March", "April", "March", "April",
                        "May", "June", "July", "August", "September"
                    ),
                    pruning_count = emptyList(),
                    seeds = 0,
                    maintenance = null,
                    care_guides = "http://perenual.com/api/species-care-guide-list?species_id=20&key=sk-1ky066d169c2847a46668",
                    soil = emptyList(),
                    growth_rate = "Low",
                    drought_tolerant = false,
                    salt_tolerant = false,
                    thorny = false,
                    invasive = false,
                    tropical = false,
                    indoor = false,
                    care_level = "Medium",
                    pest_susceptibility = emptyList(),
                    pest_susceptibility_api = "Coming Soon",
                    flowers = false,
                    flowering_season = null,
                    flower_color = "",
                    cones = false,
                    fruits = false,
                    edible_fruit = false,
                    edible_fruit_taste_profile = "Coming Soon",
                    fruit_nutritional_value = "Coming Soon",
                    fruit_color = emptyList(),
                    harvest_season = null,
                    leaf = true,
                    leaf_color = listOf("gold", "yellow"),
                    edible_leaf = false,
                    cuisine = false,
                    medicinal = false,
                    poisonous_to_humans = 0,
                    poisonous_to_pets = 0,
                    description = "The Golden Fullmoon Maple is an amazing plant species due to its dazzling yellow-gold leaves, which scintillate in the sun. This tree is a sight to behold, especially in autumn, when the leaves turn a richer golden color. Its bark is smooth and light in color and its branches are gently curved and attractively shaped. This species of maple is easy to grow and thrives in full sun or partial shade, along with well-drained, fertile soil. It is also resistant to most pests and diseases, making it an ideal choice for the garden or backyard. With its graceful form and beautiful leaves, the Golden Fullmoon Maple is truly a stand-out in the landscape.",
                    default_image = DefaultImage(
                        license = 45,
                        license_name = "Attribution-ShareAlike 3.0 Unported (CC BY-SA 3.0)",
                        license_url = "https://creativecommons.org/licenses/by-sa/3.0/deed.en",
                        original_url = "https://perenual.com/storage/species_image/20_acer_japonicum_aureum/og/2560px-Acer_shirasawanum_27Aureum27.jpg",
                        regular_url = "https://perenual.com/storage/species_image/20_acer_japonicum_aureum/regular/2560px-Acer_shirasawanum_27Aureum27.jpg",
                        medium_url = "https://perenual.com/storage/species_image/20_acer_japonicum_aureum/medium/2560px-Acer_shirasawanum_27Aureum27.jpg",
                        small_url = "https://perenual.com/storage/species_image/20_acer_japonicum_aureum/small/2560px-Acer_shirasawanum_27Aureum27.jpg",
                        thumbnail = "https://perenual.com/storage/species_image/20_acer_japonicum_aureum/thumbnail/2560px-Acer_shirasawanum_27Aureum27.jpg"
                    ),
                    other_images = "Upgrade Plan To Supreme For Access https://perenual.com/subscription-api-pricing. I'm sorry"
                )

                // Set the dummy data as the state
                _plantDetail.value = dummyPlantDetail
            } catch (e: Exception) {
                e.message?.let { Log.e("Plant Error", it) }
            }
        }
    }

}
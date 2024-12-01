package com.example.leaflove.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.leaflove.MyApp
import com.example.leaflove.data.dao.PlantDetailDao
import com.example.leaflove.data.dao.PlantSpeciesDao
import com.example.leaflove.data.entities.PlantDetailEntity
import com.example.leaflove.data.entities.PlantSpeciesEntity
import com.example.leaflove.data.map.PlantDetailMapper.plantDetailToEntity
import com.example.leaflove.data.map.PlantMapper.plantToEntity
import com.example.leaflove.data.map.PlantMapper.plantToEntityList
import com.example.leaflove.data.models.DefaultImage
import com.example.leaflove.data.models.DepthWaterRequirement
import com.example.leaflove.data.models.Dimensions
import com.example.leaflove.data.models.Hardiness
import com.example.leaflove.data.models.HardinessLocation
import com.example.leaflove.data.models.MyPlantDetailModel
import com.example.leaflove.data.models.PlantDetailResponseModel
import com.example.leaflove.data.models.PlantListResponseModel
import com.example.leaflove.data.models.PlantSpecies
import com.example.leaflove.data.models.WateringBenchmark
import com.example.leaflove.data.repositories.PlantRepository
import com.example.leaflove.services.PerenualAPIService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log


class PlantViewModel(
    private val plantRepository: PlantRepository,
    private val plantServices: PerenualAPIService
): ViewModel() {

    suspend fun initializeDAOPlant() {
        coroutineScope {
            if (plantRepository.checkIsEmpty()) {
                fetchPlantList();
            }
        }
    }

    init {
        viewModelScope.launch {
            initializeDAOPlant()
            fetchPlantListFromRoom()
        }
    }

    private val _plantDetail = mutableStateOf(PlantDetailEntity())
    val plantDetail: State<PlantDetailEntity> = _plantDetail

    private val _plantList = mutableStateOf<List<PlantSpeciesEntity>>(emptyList())
    val plantList = _plantList

    private val _plantSearchList = mutableStateOf<List<PlantSpeciesEntity>>(emptyList())
    val plantSearchList = _plantSearchList

    private val _plantSelectedItem = mutableStateOf<PlantSpeciesEntity?>(null)
    val plantSelectedItem = _plantSelectedItem

    private val _plantImageUploadUri = mutableStateOf<Uri>(Uri.EMPTY)
    val plantImageUploadUri = _plantImageUploadUri


    private val _funFacts = mutableStateOf<List<String>>(emptyList())
    val funFacts = _funFacts


    fun loadFunFacts(context: Context) {
        viewModelScope.launch {
            try {
                val inputStream = context.assets.open("FunFacts.json")
                val jsonString = inputStream.bufferedReader().use { it.readText() }
                val funFactList: List<String> = Gson().fromJson(jsonString, object : TypeToken<List<String>>() {}.type)
                Log.d("Tes: ", funFactList.toString())
                _funFacts.value = funFactList
                Log.d("FunFacts", "Fun facts loaded: ${_funFacts.value.size}")
            } catch (e: Exception) {
                Log.e("FunFacts Error", e.message ?: "Error loading fun facts")
            }
        }
    }

    fun fetchPlantSearchList(query: String){
        viewModelScope.launch{
            try{
                if(query.isBlank()){
                    clearSearchResults()
                } else {
                    _plantSearchList.value = plantRepository.searchSuggestionPlant(query)
                }
                    Log.d("plantsearch", _plantSearchList.value.toString())

            } catch (e : Exception){
                    e.message?.let {Log.e("Plant Error", it)}
            }
        }
    }



    fun clearSearchResults() {
        _plantSearchList.value = emptyList()
    }


    fun selectItem(plantSpeciesEntity: PlantSpeciesEntity){
        _plantSelectedItem.value = plantSpeciesEntity
    }

    fun fetchPlantList() {
        val allPlants = mutableListOf<PlantSpecies>()
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    for (page in 1..5) {
                        try {
                            val response = plantServices.getPlantList(page = page)
                            response.data?.let { allPlants.addAll(it) } // Assuming `data` contains the list of plants
                        } catch (e: Exception) {
                            println("Error fetching page $page: ${e.message}")
                        }
                    }
                }
                val result : List<PlantSpeciesEntity>?
                result = plantToEntityList(allPlants)
                _plantList.value = result
                plantRepository.insertPlants(result)
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

    fun setImageUploadUri(uri: Uri){
        _plantImageUploadUri.value = uri;
    }

    fun filterPlantList(query: String) {
        viewModelScope.launch {
            try {
                val filteredPlants = if (query.isBlank()) {
                    // Reset to all plants if the query is empty
                    plantRepository.getAllPlants()
                } else {
                    // Filter plants by name (case-insensitive)
                    plantRepository.getAllPlants().filter { plant ->
                        plant.common_name?.contains(query, ignoreCase = true) == true
                    }
                }
                _plantList.value = filteredPlants
            } catch (e: Exception) {
                e.message?.let { Log.e("Plant Error", it) }
            }
        }
    }

    fun fetchPlantDetails(id: Int){
        viewModelScope.launch {
            try{
                if(!plantRepository.checkIsFilledPlantDetail(id)){
                    Log.d("Cek", (!plantRepository.checkIsFilledPlantDetail(id)).toString())
                    _plantDetail.value = plantRepository.getPlantDetails(id)
                    Log.d("test ambil", _plantDetail.value.toString()   )
                } else {
                    Log.d("Cek", (!plantRepository.checkIsFilledPlantDetail(id)).toString())
                    val response = plantServices.getPlantDetail(id)
                    _plantDetail.value = plantDetailToEntity(response)
                    insertIntoPlantDetailsRoom(response)
                }
            } catch (e: Exception){
            e.message?.let{ Log.e("Plant Detail Error", it)}
            }
        }

    }



    fun insertIntoPlantDetailsRoom(plantDetailResponse: PlantDetailResponseModel){
        viewModelScope.launch{
            try{
                val mappedPlantDetail = plantDetailToEntity(plantDetailResponse);
                plantRepository.insertPlantDetail(mappedPlantDetail);
                Log.d("Test masukin",mappedPlantDetail.toString())
            } catch (e: Exception) {
                e.message?.let{ Log.e("Plant Detail Error", it)}

            }
        }
    }

    fun sortPlantList(by: String = "common_name", ascending: Boolean = true) {
        viewModelScope.launch {
            try {
                Log.d("Sort: ", "Check")
                val sortedPlants = when (by) {
                    "common_name" -> {
                        if (ascending) {
                            plantRepository.getAllPlants().sortedBy { it.common_name }
                        } else {
                            plantRepository.getAllPlants().sortedByDescending { it.common_name }
                        }
                    }
                    "scientific_name" -> {
                        if (ascending) {
                            plantRepository.getAllPlants().sortedBy { it.scientific_name }
                        } else {
                            plantRepository.getAllPlants().sortedByDescending { it.scientific_name }
                        }
                    }
                    else -> _plantList.value // Default: do not sort
                }
                Log.d("Sort: ", sortedPlants.toString())
                _plantList.value = sortedPlants
            } catch (e: Exception) {
                e.message?.let { Log.e("Sort Error", it) }
            }
        }
    }



}
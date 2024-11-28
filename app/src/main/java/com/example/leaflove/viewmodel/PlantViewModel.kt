package com.example.leaflove.viewmodel

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
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.log


class PlantViewModel(private val plantRepository: PlantRepository): ViewModel() {

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


    private val plantServices: PerenualAPIService = PerenualAPIService.create()


    private val _plantState = mutableStateOf(PlantListResponseModel())
    val plantState = _plantState

    private val _plantDetail = mutableStateOf(PlantDetailEntity())
    val plantDetail: State<PlantDetailEntity> = _plantDetail

    private val _plantList = mutableStateOf<List<PlantSpeciesEntity>>(emptyList())
    val plantList = _plantList

    private val _plantSearchList = mutableStateOf<List<PlantSpeciesEntity>>(emptyList())
    val plantSearchList = _plantSearchList

    private val _plantSelectedItem = mutableStateOf<PlantSpeciesEntity?>(null)
    val plantSelectedItem = _plantSelectedItem

    private val _plantImageUploadUrl = mutableStateOf<String>("https://media.istockphoto.com/id/1380361370/photo/decorative-banana-plant-in-concrete-vase-isolated-on-white-background.jpg?s=612x612&w=0&k=20&c=eYADMQ9dXTz1mggdfn_exN2gY61aH4fJz1lfMomv6o4=")
    val plantImageUploadUrl = _plantImageUploadUrl

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
        viewModelScope.launch {
            try {
                val response = plantServices.getPlantList()


                _plantState.value = response
                var mappedPlantEntity = plantState.value.data
                var result : List<PlantSpeciesEntity>? = emptyList();
                if (mappedPlantEntity != null) {
                    result = plantToEntityList(mappedPlantEntity)
                }
                if (result != null) {
                    plantRepository.insertPlants(result)
                }
                Log.d("Tess", _plantState.value.toString())
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

    fun setImageUploadUrl(url: String){
        _plantImageUploadUrl.value = url;
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

    fun fetchPlantDetails(id: Int = 2){
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


}
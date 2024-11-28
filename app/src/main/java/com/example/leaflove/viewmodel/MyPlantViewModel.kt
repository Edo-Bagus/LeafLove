package com.example.leaflove.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leaflove.data.entities.PlantDetailEntity
import com.example.leaflove.data.entities.PlantSpeciesEntity
import com.example.leaflove.data.map.PlantDetailMapper.plantDetailToEntity
import com.example.leaflove.data.repositories.PlantRepository
import com.example.leaflove.services.PerenualAPIService
import com.example.leaflove.utils.cleanString
import com.example.leaflove.utils.parseStringList
import kotlinx.coroutines.launch

class MyPlantViewModel(
    private val plantRepository: PlantRepository
) : ViewModel() {
    private val _funFactList = mutableStateOf<List<String>>(emptyList())
    val funFactList = _funFactList

    private val plantServices: PerenualAPIService = PerenualAPIService.create()

    init {
        Log.d("Tes: ", "sdfbseb")
    }

    fun loadFunFactList(id: Int, name: String){
        Log.d("Tes: ", id.toString() + name)
        viewModelScope.launch {
            try{
                var plant: PlantDetailEntity;
                if(plantRepository.checkIsFilledPlantDetail(id)) {
                    val response = plantServices.getPlantDetail(id)
                    plant = plantDetailToEntity(response);
                    plantRepository.insertPlantDetail(plant);
                }else{
                    plant = plantRepository.getPlantDetails(id)
                }
                funFactList.value += funFactList.value + (name + " is a " + plant.common_name + " and has a scientific name of " + cleanString(plant.scientific_name) + ".")
                val origins = plant.origin?.let { parseStringList(it) } ?: emptyList()
                val truncatedOrigins = origins.take(3) + if (origins.size > 3) listOf("etc") else emptyList()
                funFactList.value += name + " comes from " + truncatedOrigins.joinToString(", ") + "."
            }catch(e: Exception){
                Log.e("Fun Fact Error", e.message ?: "Error loading fun facts")
            }
        }
    }
}
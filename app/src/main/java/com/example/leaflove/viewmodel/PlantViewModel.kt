package com.example.leaflove.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leaflove.data.PlantListResponseModel
import com.example.leaflove.services.PerenualAPIService
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
}
package com.example.leaflove.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leaflove.services.OpenWeatherAPIService
import com.example.leaflove.data.WeatherResponse
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val weatherServices: OpenWeatherAPIService = OpenWeatherAPIService.create()

    private val _weatherState = mutableStateOf(WeatherResponse())
    val weatherState = _weatherState

    private val _humidityState = mutableStateOf("")
    val humidityState = _humidityState.value

    fun fetchWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                _weatherState.value = weatherServices.getWeather(lat, lon)
                Log.d("Weather Data", weatherState.value.toString())
            } catch (e: Exception) {
                e.message?.let { Log.e("Weather Error", it) }
            }
        }
    }
}
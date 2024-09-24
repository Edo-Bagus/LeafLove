package com.example.leaflove
import WeatherResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

interface ApiServices {
    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String = "166200bb3414fdf4ba3a1ae33092d8fc"
    ): WeatherResponse
}

class MainViewModel: ViewModel() {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val weatherServices: ApiServices = retrofit.create(ApiServices::class.java)

    private val _weatherState = mutableStateOf(WeatherResponse())
    var weatherState = _weatherState

    var lat:Double = 14.123342;
    var lon:Double = 7.5324341;

    init{
        try{
            viewModelScope.launch{
                fetchWeather()
                Log.d("Test", weatherState.value.toString())
            }
        }
        catch(e: Exception) {
            e.message?.let { Log.e("error coy: ", it) }
        }
    }
    suspend fun fetchWeather(){
        try{
            _weatherState.value = weatherServices.getWeather(this.lat, this.lon)
        }
        catch(e: Exception) {
            e.message?.let { Log.e("error coy: ", it) }
        }
    }


}
package com.example.leaflove.services

import com.example.leaflove.BuildConfig
import com.example.leaflove.data.models.WeatherResponseModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

val weatherApiKey = BuildConfig.WEATHER_API_KEY

interface OpenWeatherAPIService {
    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String = weatherApiKey
    ): WeatherResponseModel

    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

        fun create(): OpenWeatherAPIService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(OpenWeatherAPIService::class.java)
        }
    }
}

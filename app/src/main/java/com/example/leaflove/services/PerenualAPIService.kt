package com.example.leaflove.services

import com.example.leaflove.BuildConfig
import com.example.leaflove.data.PlantListResponse
import com.example.leaflove.data.WeatherResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

val plantApiKey = BuildConfig.PLANT_API_KEY

interface PerenualAPIService {
    @GET("species-list")
    suspend fun getPlantList(
        @Query("key") apiKey: String = plantApiKey
    ): PlantListResponse

    companion object {
        private const val BASE_URL = "https://perenual.com/api/"

        fun create(): PerenualAPIService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(PerenualAPIService::class.java)
        }
    }
}

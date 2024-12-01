package com.example.leaflove.services

import com.example.leaflove.BuildConfig
import com.example.leaflove.data.models.PlantDetailResponseModel
import com.example.leaflove.data.models.PlantListResponseModel
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

val plantApiKey = BuildConfig.PLANT_API_KEY

interface PerenualAPIService {
    @GET("species-list")
    suspend fun getPlantList(
        @Query("key") apiKey: String = plantApiKey,
        @Query("page") page: Int
    ): PlantListResponseModel

    @GET("species/details/{plant_id}")
    suspend fun getPlantDetail(
        @Path("plant_id") plantId: Int,
        @Query("key") apiKey: String = plantApiKey
    ): PlantDetailResponseModel

    companion object {
        private const val BASE_URL = "https://perenual.com/api/"


        fun create(): PerenualAPIService {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(PerenualAPIService::class.java)
        }
    }
}

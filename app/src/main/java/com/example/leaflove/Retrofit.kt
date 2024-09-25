//package com.example.leaflove
//
//import WeatherResponse
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.http.GET
//import retrofit2.http.Query
//
//
//val retrofit:Retrofit = Retrofit.Builder()
//    .baseUrl("https://api.openweathermap.org/data/2.5")
//    .addConverterFactory(GsonConverterFactory.create())
//    .build()
//
//
//interface ApiServices {
//    @GET("/weather")
//    suspend fun getWeather(@Query("lat") lat: Double,
//                           @Query("lon") lon: Double,
//                           @Query("appid") appid: String = "166200bb3414fdf4ba3a1ae33092d8fc"):WeatherResponse
//}
//
//val weatherServices: ApiServices = retrofit.create(ApiServices::class.java)
//

package com.example.leaflove.services

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File
import com.cloudinary.android.MediaManager


interface CloudinaryApi {
    @Multipart
    @POST("image/upload")
    fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("upload_preset") uploadPreset: String
    ): Call<CloudinaryResponse>
}

// Define a response model
data class CloudinaryResponse(
    val secure_url: String // URL of the uploaded image
)


object RetrofitInstance {
    private const val BASE_URL = "https://api.cloudinary.com/v1_1/ddrnhafts/"

    val api: CloudinaryApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CloudinaryApi::class.java)
    }
}


fun createImagePart(filePath: String, partName: String): MultipartBody.Part {
    val file = File(filePath)
    val requestFile = file.asRequestBody("multipart/form-data".toMediaType())
    Log.d("testbuatimage",  file.toString())
    return MultipartBody.Part.createFormData(partName, file.name, requestFile)
}



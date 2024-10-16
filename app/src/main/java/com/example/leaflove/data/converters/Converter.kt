package com.example.leaflove.data.converters


import androidx.room.TypeConverter
import com.example.leaflove.data.models.DefaultImage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    // For List<String> fields
    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return Gson().fromJson(value, object : TypeToken<List<String>>() {}.type)
    }

    // For DefaultImage object
    @TypeConverter
    fun fromDefaultImage(value: DefaultImage?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toDefaultImage(value: String?): DefaultImage? {
        return Gson().fromJson(value, DefaultImage::class.java)
    }
}

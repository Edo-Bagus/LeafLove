package com.example.leaflove.data.entities

import com.example.leaflove.data.converters.Converters
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "plant_detail")
@TypeConverters(Converters::class)  // Use the converters to handle complex types
data class PlantDetailEntity (
    @PrimaryKey val id: Int,
    val common_name: String?,
    val scientific_name: String?,  // We can store the List<String> as a JSON string
    val other_name: String?,      // Nullable field stored as a JSON string
    val cycle: String?,
    val watering: String?,
    val sunlight: String?,         // Also stored as JSON string
    val default_image: String?     // Store the DefaultImage object as a JSON string
)

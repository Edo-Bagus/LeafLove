package com.example.leaflove.data.entities

import com.example.leaflove.data.converters.Converters
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import androidx.room.ForeignKey
import com.example.leaflove.data.models.DefaultImage

@Entity(
    tableName = "plant_species",
    foreignKeys = [
        ForeignKey(
            entity = PlantDetailEntity::class,
            parentColumns = ["id"],
            childColumns = ["detailId"],
            onDelete = ForeignKey.CASCADE  // or other option based on your requirement
        )
    ]
)
@TypeConverters(Converters::class)  // Use the converters to handle complex types
data class PlantSpeciesEntity(
    @PrimaryKey val id: Int,
    val common_name: String?,
    val scientific_name: String?,  // We can store the List<String> as a JSON string
    val other_name: String?,      // Nullable field stored as a JSON string
    val cycle: String?,
    val watering: String?,
    val sunlight: String?,         // Also stored as JSON string
    val default_image: String?,     // Store the DefaultImage object as a JSON string
    val detailId: Int?             // New foreign key reference to PlantDetailEntity
)


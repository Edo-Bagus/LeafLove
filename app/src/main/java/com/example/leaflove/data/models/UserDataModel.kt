package com.example.leaflove.data.models


import com.example.leaflove.data.entities.PlantDetailEntity
import com.google.firebase.Timestamp

data class UserDataModel(
    val username: String = "",
    val email: String = "", // Default to an empty string
    val password: String = "", // Default to an empty string
    var my_plants: MutableList<MyPlantModel> = mutableListOf() // Default to an empty list
)

data class MyPlantModel(
    val plant_name: String = "", // Default to an empty string
    val plant_age: Timestamp = Timestamp.now(), // Default to the current timestamp
    val plant_last_watered: Timestamp = Timestamp.now(), // Default to the current timestamp
    val plant_to_be_watered: Timestamp = Timestamp.now(), // Default to the current timestamp
    val plant_fk: Int = 1,
    val plant_status: Int = 1, // Default to 0
    val plant_image_url: String = "https://media.istockphoto.com/id/1380361370/photo/decorative-banana-plant-in-concrete-vase-isolated-on-white-background.jpg?s=612x612&w=0&k=20&c=eYADMQ9dXTz1mggdfn_exN2gY61aH4fJz1lfMomv6o4="
)

data class MyPlantDetailModel(
    val myPlantModel: MyPlantModel = MyPlantModel(),
    val plantDetailEntity: PlantDetailEntity = PlantDetailEntity(),
)

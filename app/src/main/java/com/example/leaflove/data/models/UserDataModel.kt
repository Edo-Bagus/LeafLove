package com.example.leaflove.data.models


import com.google.firebase.Timestamp

data class UserDataModel(
    val email: String = "", // Default to an empty string
    val password: String = "", // Default to an empty string
    val my_plants: List<MyPlantModel> = emptyList() // Default to an empty list
)

data class MyPlantModel(
    val plant_name: String = "", // Default to an empty string
    val plant_age: Timestamp = Timestamp.now(), // Default to the current timestamp
    val plant_last_watered: Timestamp = Timestamp.now(), // Default to the current timestamp
    val plant_to_be_watered: Timestamp = Timestamp.now(), // Default to the current timestamp
    val plant_fk: Int = 0, // Default to 0
    val plant_status: Int = 0 // Default to 0
)

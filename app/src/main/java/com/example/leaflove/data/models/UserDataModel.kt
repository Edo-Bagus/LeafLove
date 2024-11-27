package com.example.leaflove.data.models


import com.google.firebase.Timestamp

data class UserDataModel(
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
    val plant_status: Int = 1 // Default to 0
)

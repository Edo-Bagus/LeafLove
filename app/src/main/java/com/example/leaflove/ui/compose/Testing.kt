package com.example.leaflove.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.example.leaflove.MyApp
import com.example.leaflove.data.dao.PlantSpeciesDao
import com.example.leaflove.data.entities.PlantSpeciesEntity
import com.example.leaflove.data.map.PlantMapper
import com.example.leaflove.data.models.PlantListResponseModel
import com.example.leaflove.data.models.PlantSpecies
import com.example.leaflove.viewmodel.PlantViewModel
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Testing(navHost: NavHostController, plantViewModel: PlantViewModel) {
    val plantState = plantViewModel.plantState.value
    val coroutineScope = rememberCoroutineScope()

    val plantSpeciesDao = MyApp.getDatabase().plantSpeciesDao()

    // State to hold the fetched plants
    var plantEntities by remember { mutableStateOf<List<PlantSpeciesEntity>>(emptyList()) }

    // Trigger fetching the plant list when the composable is launched
    LaunchedEffect(Unit) {
        plantViewModel.fetchPlantListDummy()
    }

    // Use Column to arrange elements vertically
    Column(
        modifier = Modifier
            .fillMaxSize() // Use available space
            .padding(16.dp) // Add padding to the column
    ) {
        // Submit button to save plants to database
        Button(onClick = {
            coroutineScope.launch {
                ButtonHandler(plantState, plantSpeciesDao)
            }
        }) {
            Text("Submit")
        }

        Spacer(modifier = Modifier.height(16.dp)) // Add space between buttons

        // Button to fetch and display the plants from the database
        Button(onClick = {
            coroutineScope.launch {
                plantEntities = plantSpeciesDao.getAllPlants()
            }
        }) {
            Text("Show Plants")
        }

        Spacer(modifier = Modifier.height(16.dp)) // Add space between buttons

        // Display the fetched plant entries
        if (plantEntities.isNotEmpty()) {
            // Display the plant entities
            for (plant in plantEntities) {
                Text(text = "Plant: ${plant.common_name}") // Adjust according to your entity fields
                Spacer(modifier = Modifier.height(4.dp)) // Space between plant entries
            }
        } else {
            Text(text = "No plants found") // Message when no plants are present
        }
    }
}

fun savePlantsToDatabase(plantList: List<PlantSpecies>) : List<PlantSpeciesEntity> {
    val plantEntities = plantList.map { plant ->
        PlantMapper.plantToEntity(plant)
    }
    return plantEntities
}

suspend fun ButtonHandler(plantState: PlantListResponseModel, plantSpeciesDao: PlantSpeciesDao){
    val plantEntities = plantState.data?.let { savePlantsToDatabase(it) }
    if (plantEntities != null) {
        plantSpeciesDao.insertAll(plantEntities)
    }
}

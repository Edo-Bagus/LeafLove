package com.example.leaflove.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Data class untuk Plant
data class Plant(
    val nama: String,
    val status: String,
    val image: Int
)

// Fungsi untuk menampilkan satu kartu Plant
@Composable
fun MyPlantCard(plant: Plant, screenHeight: Dp, screenWidth: Dp) {
    Column(modifier = Modifier
        .padding(8.dp)
        .offset(x = screenWidth * 0.01f)
        .clip(
            RoundedCornerShape(20.dp),
        )) {
        Box(modifier = Modifier
            .background(Color.White)
            .height(screenHeight * 0.25f)
            .width(screenWidth * 0.5f)){
            Column {
                Text(text = plant.nama)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = plant.status)
            }

        }
    }
}

// Fungsi untuk menampilkan grid dari PlantCard
@Composable
fun MyPlantGrid(plants: List<Plant>, screenHeight: Dp, screenWidth: Dp) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Menampilkan 2 kolom
        contentPadding = PaddingValues(8.dp)
    ) {
        items(plants) { plant ->
            MyPlantCard(plant = plant, screenHeight, screenWidth)
        }
    }
}

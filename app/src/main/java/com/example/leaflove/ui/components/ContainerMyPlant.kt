package com.example.leaflove.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import com.example.leaflove.R

// Data class untuk Plant
data class Plant(
    val nama: String,
    val status: String,
    val image: String,
    val to_water: String? = null,
    val last_water: String? = null,
    val age: String? = null
)

// Fungsi untuk menampilkan satu kartu Plant
@Composable
fun MyPlantCard(plant: Plant, screenHeight: Dp, screenWidth: Dp) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .offset(x = screenWidth * 0.01f)
            .clip(RoundedCornerShape(20.dp))
    ) {
        Box(
            modifier = Modifier


                .shadow(
                    elevation = 1.dp,
                    shape = RoundedCornerShape(10.dp),
                    ambientColor = Color.Gray.copy(alpha = 0.2f), // Bayangan abu-abu terang
                    spotColor = Color.Gray.copy(alpha = 0.2f)
                )
                .height(screenHeight * 0.25f)
                .width(screenWidth * 0.5f)
                .zIndex(1f) // Memastikan di atas elemen lain
        )

        {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 1.dp,
                        color = Color.White
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,

            ) {
                Image(
                    painter = rememberAsyncImagePainter(plant.image),
                    contentDescription = null,
                    modifier = Modifier.size(height = screenHeight * 0.15f,width= screenWidth * 0.30f ))

                Text(text = plant.nama,
                    color = Color.Black,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start,
                    fontFamily = FontFamily.SansSerif)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = plant.status,
                    color = Color.Gray,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start,
                    fontFamily = FontFamily.Default
                )
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

            item {

                // Menambahkan ruang ekstra setelah grid
                Spacer(modifier = Modifier.height(100.dp))  // Ini memberikan ruang ekstra di bagian bawah
            }
        }


}

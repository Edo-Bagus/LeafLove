package com.example.leaflove.ui.screen.bottomNav

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.leaflove.R
import com.example.leaflove.ui.components.MyPlantGrid
import com.example.leaflove.ui.components.Plant
import com.example.leaflove.ui.theme.BasicGreen
import com.example.leaflove.viewmodel.LocationViewModel
import com.example.leaflove.viewmodel.WeatherViewModel
import com.example.leaflove.viewmodel.AuthViewModel
import org.koin.compose.koinInject

enum class Status(val rating: Int) {
    Good(1),
    Mediocre(2),
    Bad(3);
}

@Composable
fun MyPlantScreen(navHost: NavHostController, weatherViewModel: WeatherViewModel, locationViewModel: LocationViewModel){

    val weatherState by weatherViewModel.weatherState

    val tempCelsius = weatherState.main?.temp?.let { it - 273.15 }
    val tempCelsiusString = tempCelsius?.let { String.format("%.2f", it) } ?: "Loading"
    val humidity = weatherState.main?.humidity?.toString() ?: "Loading"
    val weatherMain = weatherState.weather?.firstOrNull()?.main ?: "Loading"

    val image3 = painterResource(R.drawable.menumyplant)
    val image4 = painterResource(R.drawable.footermyplant)
    var username by remember { mutableStateOf("") }
    val authViewModel = koinInject<AuthViewModel>()


    val plants = mutableListOf<Plant>()
        for(plant in authViewModel.userData.value?.my_plants!!){
            val status = Status.values().find { it.rating == plant.plant_status } ?: Status.Mediocre
            plants.add(Plant(nama = plant.plant_name, status = status.name, image = plant.plant_image_url))
        }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val screenHeight = maxHeight
        val screenWidth = maxWidth

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight * 0.3f)
                .offset(y = screenHeight * 0.1f)
                .zIndex(1f)
        ) {
            Column {
                Text(
                    text = "My Plant",
                    fontWeight = FontWeight.Bold,
                    color = BasicGreen,
                    fontSize = 32.sp,
                    modifier = Modifier.offset(x = screenWidth * 0.1f)

                )
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "Temperature")
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "Weather")
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "Humidity")
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = tempCelsiusString)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = weatherMain)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = humidity)
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight * 0.7f)
                .offset(y = screenHeight * 0.3f)
                .background(Color.White)
                .zIndex(1f)
        )
        {
            if (plants.isEmpty()) {
                // Display "No Data" if plants are empty
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No Data",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                }
            } else {
                // Display plants grid
                MyPlantGrid(navHost, plants = plants, screenHeight, screenWidth)
            }        }
    }
}
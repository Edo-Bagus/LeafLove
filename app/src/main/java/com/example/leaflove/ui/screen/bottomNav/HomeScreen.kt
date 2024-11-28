package com.example.leaflove.ui.screen.bottomNav

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.leaflove.services.LocationUtils
import com.example.leaflove.viewmodel.LocationViewModel
import com.example.leaflove.R
import com.example.leaflove.ui.components.Plant
import com.example.leaflove.data.entities.PlantDetailEntity
import com.example.leaflove.ui.theme.BasicGreen
import com.example.leaflove.viewmodel.WeatherViewModel
import com.example.leaflove.ui.theme.ButtonGreen
import com.example.leaflove.utils.calculatePlantAgeInDays
import com.example.leaflove.viewmodel.AuthViewModel
import com.google.firebase.Timestamp
import org.koin.compose.koinInject
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Locale
import com.example.leaflove.viewmodel.PlantViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navHost: NavHostController, plantViewModel: PlantViewModel, weatherViewModel: WeatherViewModel, locationViewModel: LocationViewModel) {

    val authViewModel = koinInject<AuthViewModel>()

    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    val plants = mutableListOf<Plant>()
    for(plant in authViewModel.userData.value?.my_plants!!){
        val status = Status.values().find { it.rating == plant.plant_status } ?: Status.Mediocre
        plants.add(Plant(
            nama = plant.plant_name,
            status = status.name,
            image = R.drawable.contoh_tanaman,
            to_water = dateFormatter.format(plant.plant_to_be_watered.toDate()),
            last_water = dateFormatter.format(plant.plant_last_watered.toDate()),
            age = calculatePlantAgeInDays(plant.plant_age)
        ))
    }
    val plant = remember(plants) { plants.randomOrNull() }

    var customfont = FontFamily(
        Font(R.font.baloo_font, weight = FontWeight.Normal),
        Font(R.font.baloo_bold, weight = FontWeight.Bold)
    )
    val context = LocalContext.current
    plantViewModel.loadFunFacts(context)
    val funFacts = remember(plantViewModel.funFacts.value) {
        plantViewModel.funFacts.value.random()
    }
    val locationUtils = remember { LocationUtils(context, locationViewModel) }
    val location = locationViewModel.locationState

    val cuaca = weatherViewModel.weatherState.value.weather?.firstOrNull()?.main ?: "Loading"
    val humidity = weatherViewModel.weatherState.value.main?.humidity ?: "Loading"
    val tempKelvin = weatherViewModel.weatherState.value.main?.temp ?: 99999.99
    var tempCelcius = tempKelvin?.minus(273)
    var tempCelciusString = String.format("%.2f", tempCelcius)
    // Start location updates when the composable is entered
    LaunchedEffect(Unit) {
        if (locationUtils.hasLocationPermission(context)) {
            locationUtils.requestLocationUpdate()
            weatherViewModel.fetchWeather(location.value.latitude, location.value.longitude)
        } else {
            requestLocationPermissions(context)
        }
    }



    DisposableEffect(Unit) {
        onDispose {
            locationUtils.stopLocationUpdates()
        }
    }

    // Use BoxWithConstraints to get the screen size
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val screenHeight = maxHeight
        val screenWidth = maxWidth

        val scope = rememberCoroutineScope()
        val scaffoldState = rememberBottomSheetScaffoldState()

        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContainerColor = Color.White,
            sheetPeekHeight = screenHeight * 0.5f,
            sheetContent = {
                Column(
                    modifier = Modifier
                        .heightIn(max = screenHeight * 0.86f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.weight(0.1f))

                    Box(
                        modifier = Modifier
                            .shadow(
                                elevation = 10.dp,
                                shape = RoundedCornerShape(20.dp),
                                clip = false
                            )
                            .background(ButtonGreen)
                            .width(screenWidth * 0.8f)
                            .height(screenHeight * 0.1f)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Row (modifier = Modifier
                            .align(Alignment.Center) ) {
                            Spacer(modifier = Modifier.weight(1f))
                            Text(text = cuaca,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(text = humidity.toString(),
                                color = Color.White)
                            Spacer(modifier = Modifier.weight(1f))
                            Text(text = tempCelciusString + "˚C",
                                color = Color.White)
                            Spacer(modifier = Modifier.weight(1f))
                        }

                    }

                    Spacer(modifier = Modifier.weight(0.5f))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Box(
                            modifier = Modifier
                                .shadow(
                                    elevation = 10.dp,
                                    shape = RoundedCornerShape(20.dp),
                                    clip = false
                                )
                                .background(Color.White)
                                .size(screenWidth * 0.2f)
                        ){
                            Column (
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .clickable
                                    {
                                        navHost.navigate("transaction")
                                    },
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                Image(
                                    painter = painterResource(R.drawable.cart),
                                    contentDescription = "Store",
                                    alignment = Alignment.Center,
                                    modifier = Modifier.size(screenWidth * 0.1f)

                                )
                                Text(text = "Store")
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Box(
                            modifier = Modifier
                                .shadow(
                                    elevation = 10.dp,
                                    shape = RoundedCornerShape(20.dp),
                                    clip = false
                                )
                                .background(Color.White)
                                .size(screenWidth * 0.2f)
                        ){
                            Column (
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .clickable
                                    {
                                        navHost.navigate("camera")
                                    },
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                Image(
                                    painter = painterResource(R.drawable.baseline_camera_alt_24_selected),
                                    contentDescription = "Camera",
                                    alignment = Alignment.Center,
                                    modifier = Modifier.size(screenWidth * 0.1f)

                                )
                                Text(text = "AR")
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Box(
                            modifier = Modifier
                                .shadow(
                                    elevation = 10.dp,
                                    shape = RoundedCornerShape(20.dp),
                                    clip = false
                                )
                                .background(Color.White)
                                .size(screenWidth * 0.2f)
                        ){
                            Column (
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .clickable
                                    {
                                        navHost.navigate("my_plant")
                                    },
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                Image(
                                    painter = painterResource(R.drawable.baseline_forest_24_selected),
                                    contentDescription = "Plant",
                                    alignment = Alignment.Center,
                                    modifier = Modifier.size(screenWidth * 0.1f)

                                )
                                Text(text = "My Plant")
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.weight(0.5f))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Box(
                            modifier = Modifier
                                .shadow(
                                    elevation = 10.dp,
                                    shape = RoundedCornerShape(20.dp),
                                    clip = false
                                )
                                .background(Color.White)
                                .size(screenWidth * 0.2f)
                        ){
                            Column (
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .clickable
                                    {
                                        navHost.navigate("searchScreen")
                                    },
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                Image(
                                    painter = painterResource(R.drawable.baseline_search_24),
                                    contentDescription = "Ensiklopedia",
                                    alignment = Alignment.Center,
                                    modifier = Modifier.size(screenWidth * 0.1f)

                                )
                                Text(text = "Encyclopedia", fontSize = 12.sp)
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Box(
                            modifier = Modifier
                                .shadow(
                                    elevation = 10.dp,
                                    shape = RoundedCornerShape(20.dp),
                                    clip = false
                                )
                                .background(Color.White)
                                .width(screenWidth * 0.5f)
                                .height(screenHeight * 0.1f)
                        ){
                            Text(text = funFacts,
                                modifier = Modifier
                                    .align(Alignment.Center),
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp)
                        }

                        Spacer(modifier = Modifier.weight(1f))

                    }

                    Spacer(modifier = Modifier.weight(0.5f))
                    Box(
                        modifier = Modifier
                            .shadow(
                                elevation = 10.dp,
                                shape = RoundedCornerShape(20.dp),
                                clip = false
                            )
                            .background(Color.White)
                            .width(screenWidth * 0.8f)
                            .height(screenHeight * 0.2f)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize() // Pastikan Row mengisi seluruh area
                        ) {
                            Spacer(modifier = Modifier.weight(0.1f))

                            Box(
                                modifier = Modifier
                                    .shadow(
                                        elevation = 10.dp,
                                        shape = RoundedCornerShape(10.dp),
                                        clip = false
                                    )
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color.White)
                                    .size(screenWidth * 0.3f)
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.contoh_tanaman),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }

                            Spacer(modifier = Modifier.weight(0.1f))
                            Log.d("Tes", plant.toString())
                            if(plant != null){
                                Column (
                                    modifier = Modifier.width(screenWidth * 0.2f)
                                ) {
                                        Spacer(modifier = Modifier.weight(1f))
                                        Text(
                                            text = "Name: " + plant.nama,
                                            fontFamily = customfont,
                                            fontWeight = FontWeight.Normal
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Text(
                                            text = "Age: " + plant.age,
                                            fontFamily = customfont,
                                            fontWeight = FontWeight.Normal
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                }

                                Spacer(modifier = Modifier.weight(0.1f))

                                Column (
                                    modifier = Modifier.width(screenWidth * 0.2f)
                                ){
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(
                                        text = "Last Water: " + plant.last_water,
                                        fontFamily = customfont,
                                        fontWeight = FontWeight.Normal)
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(
                                        text = "Next Water: " + plant.to_water,
                                        fontFamily = customfont,
                                        fontWeight = FontWeight.Normal)
                                    Spacer(modifier = Modifier.weight(1f))
                                }

                                Spacer(modifier = Modifier.weight(0.1f))
                            }else{
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = "No Plant Data Available",
                                    fontFamily = customfont,
                                    fontWeight = FontWeight.Normal
                                )
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }


                    Spacer(modifier = Modifier.weight(1.5f))


                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight * 0.6f)
                    .zIndex(0.8f)
                    .background(Color.Black)
                    .fillMaxSize()
            ) {
                Button(
                    onClick = { navHost.navigate("addmyplant") },
                    modifier = Modifier
                        .zIndex(1f)
                        .offset(x = screenWidth * 0.02f, y = screenHeight * 0.2f)
                        .height(screenHeight * 0.08f)
                        .shadow(
                            elevation = 8.dp, // Tinggi bayangan
                            shape = RoundedCornerShape(10.dp), // Sama dengan radius sudut tombol
                            clip = false // Tidak memotong konten berdasarkan bentuk
                        ),
                    colors = ButtonColors(
                        contentColor = BasicGreen,
                        containerColor = Color.White,
                        disabledContentColor = BasicGreen,
                        disabledContainerColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = " + Add your plant",
                        fontFamily = customfont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp)
                }
                Image(
                    painter = painterResource(R.drawable.mainmenubg),
                    contentDescription = "Background",
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(0.1f),
                    contentScale = ContentScale.Crop)
                Image(
                    painter = painterResource(R.drawable.contoh_tanaman),
                    contentDescription = "Contoh Tanaman",
                    modifier = Modifier
                        .zIndex(1f)
                        .size(size = screenHeight * 0.5f)
                        .offset(x = screenWidth * 0.3f, y = screenHeight * 0.03f)
                )
            }
        }
    }
}

private fun requestLocationPermissions(context: Context) {
    if (context is Activity) {
        // Check if the permission is not granted
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }
}

const val REQUEST_LOCATION_PERMISSION = 1

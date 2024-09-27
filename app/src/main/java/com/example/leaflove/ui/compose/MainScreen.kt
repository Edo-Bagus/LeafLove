package com.example.leaflove.ui.compose

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.leaflove.services.LocationUtils
import com.example.leaflove.viemodel.LocationViewModel
import com.example.leaflove.R
import com.example.leaflove.viemodel.WeatherViewModel
import com.example.leaflove.ui.theme.ButtonGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mainScreen(navHost: NavHostController, weatherViewModel: WeatherViewModel, locViewModel: LocationViewModel) {
    val image = painterResource(R.drawable.mainmenu)
    val image3 = painterResource(R.drawable.mainmenu2)

    val items = listOf("Camera", "Home", "My Plant")
    val icons = listOf(
        painterResource(R.drawable.baseline_photo_camera_24),
        painterResource(R.drawable.baseline_home_24),
        painterResource(R.drawable.outline_forest_24)
    )
    val itemsNavigation = listOf("ARScreen","mainscreen","myplantscreen")

    // State to keep track of selected item
    var selectedItem by remember { mutableStateOf(0) }

    val context = LocalContext.current
    val locationUtils = remember { LocationUtils(context, locViewModel) }
    val location = locViewModel.locationState

    val cuaca = weatherViewModel.weatherState.value.weather?.firstOrNull()?.main ?: "No weather data"
    val humidity = weatherViewModel.weatherState.value.main?.humidity ?: "Test"
    val tempKelvin = weatherViewModel.weatherState.value.main?.temp ?: 99999.99
    var tempCelcius = tempKelvin?.minus(273)
    var tempCelciusString = String.format("%.2f", tempCelcius)
    // Start location updates when the composable is entered
    LaunchedEffect(Unit) {
        if (locationUtils.hasLocationPermission(context)) {
            locationUtils.requestLocationUpdate()
        } else {
            requestLocationPermissions(context)
        }
    }

    location.value?.let { loc ->
        LaunchedEffect(loc.latitude, loc.longitude) {
            weatherViewModel.fetchWeather(loc.latitude, loc.longitude)
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

        // Header
        Row(
            modifier = Modifier
                .height(screenHeight * 0.1f)
                .zIndex(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(0.2f))
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(screenWidth * 0.3f),
                        clip = false
                    )
                    .width(screenWidth * 0.2f),
                colors = ButtonDefaults.buttonColors(Color.White)
            ) {
                Text(text = "Coin",
                    color = Color.Gray)
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .width(screenWidth*0.6f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Gray
                )) {
                Text(text = "Search")
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = { /*TODO*/ },
                modifier = Modifier
                    .wrapContentSize(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonGreen
                )) {
                Icon(
                    painter = painterResource(R.drawable.baseline_person_24),
                    contentDescription = "Account",
                    modifier = Modifier
                        .size(screenWidth * 0.1f)
                )
            }
            Spacer(modifier = Modifier.weight(0.1f))
        }

        // Main menu background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight * 0.5f)
                .zIndex(0.8f)
                .background(Color.Black)
        ) {
            Image(painter = painterResource(R.drawable.mainmenubg),
                contentDescription = "Background",
                modifier = Modifier
                    .size(screenWidth * 1.06f)
                    .offset(y = -screenHeight * 0.01f))
        }

        // Main content menu
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight * 0.6f)
                .offset(y = screenHeight * 0.4f)
                .zIndex(0.9f)
                .clip(
                    RoundedCornerShape(
                        topStart = screenWidth * 0.1f,
                        topEnd = screenWidth * 0.1f
                    )
                )
        ) {
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .size(screenWidth, screenHeight * 0.5f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.weight(1f))

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

                    Spacer(modifier = Modifier.weight(1f))

                    // Limit the Row to a specific height or use weight to avoid taking all space
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(), // Take a portion of available space, but leave room for other elements
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
                        )
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
                        )
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
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Box(
                        modifier = Modifier
                            .shadow(
                                elevation = 10.dp, // Elevation for drop shadow
                                shape = RoundedCornerShape(20.dp), // Rounded corners
                                clip = false // Ensure shadow is not clipped
                            )
                            .background(Color.White) // White background for the box
                            .width(screenWidth * 0.8f)
                            .height(screenHeight * 0.1f)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "No Achievement",
                            modifier = Modifier
                                .align(Alignment.Center))
                    }

                    Spacer(modifier = Modifier.weight(1f))
                }
            }

        }

        // Footer - Navigation Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight * 0.1f)
                .offset(y = screenHeight * 0.9f)
                .zIndex(1f)
        ) {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = icons[index], // Use painter for drawables
                                contentDescription = item
                            )
                        },
                        label = { Text(item) },
                        selected = selectedItem+1 == index,
                        onClick = { navHost.navigate(itemsNavigation[index])}
                    )
                }
            }
        }
    }
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun prev() {
    mainScreen(navHost = rememberNavController(), WeatherViewModel(), LocationViewModel())
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

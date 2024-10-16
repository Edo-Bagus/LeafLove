package com.example.leaflove.screen.bottomNav

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
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
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.leaflove.services.LocationUtils
import com.example.leaflove.viewmodel.LocationViewModel
import com.example.leaflove.R
import com.example.leaflove.viewmodel.WeatherViewModel
import com.example.leaflove.ui.theme.ButtonGreen
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navHost: NavHostController, weatherViewModel: WeatherViewModel, locViewModel: LocationViewModel) {


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
            weatherViewModel.fetchWeather(location.value.latitude, location.value.longitude)
            Log.d("WTF", weatherViewModel.toString())
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
                                    .align(Alignment.Center),
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
                                .width(screenWidth * 0.5f)
                                .height(screenHeight * 0.1f)
                        ){
                            Text(text = "No Achievement",
                                modifier = Modifier
                                    .align(Alignment.Center))
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
                            .height(screenHeight * 0.1f)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "My Plant",
                            modifier = Modifier
                                .align(Alignment.Center))
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
                            .height(screenHeight * 0.1f)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "Fun Fact",
                            modifier = Modifier
                                .align(Alignment.Center))
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
                Image(
                    painter = painterResource(R.drawable.mainmenubg),
                    contentDescription = "Background",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop)
            }
        }
        // Main content menu
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(screenHeight * 0.6f)
//                .offset(y = screenHeight * 0.5f)
//                .zIndex(0.9f)
//                .clip(
//                    RoundedCornerShape(
//                        topStart = screenWidth * 0.1f,
//                        topEnd = screenWidth * 0.1f
//                    )
//                )
//        ) {
//            Box( //Box yang ingin diswipe
//                modifier = Modifier
//                    .background(Color.White)
//                    .size(screenWidth, screenHeight * 0.5f)
//                    .pointerInput (Unit) {
//                        detectVerticalDragGestures { change, dragAmount ->  change.consumeAllChanges()
//                        if (dragAmount < 0){
//                            navHost.navigate("homepage2")
//                        }
//                        }
//                    },
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize(),
//                    verticalArrangement = Arrangement.Center,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Spacer(modifier = Modifier.weight(1f))
//
//                    Box(
//                        modifier = Modifier
//                            .shadow(
//                                elevation = 10.dp,
//                                shape = RoundedCornerShape(20.dp),
//                                clip = false
//                            )
//                            .background(ButtonGreen)
//                            .width(screenWidth * 0.8f)
//                            .height(screenHeight * 0.1f)
//                            .align(Alignment.CenterHorizontally)
//                    ) {
//                        Row (modifier = Modifier
//                            .align(Alignment.Center) ) {
//                            Spacer(modifier = Modifier.weight(1f))
//                            Text(text = cuaca,
//                                color = Color.White
//                            )
//                            Spacer(modifier = Modifier.weight(1f))
//                            Text(text = humidity.toString(),
//                                color = Color.White)
//                            Spacer(modifier = Modifier.weight(1f))
//                            Text(text = tempCelciusString + "˚C",
//                                color = Color.White)
//                            Spacer(modifier = Modifier.weight(1f))
//                        }
//
//                    }
//
//                    Spacer(modifier = Modifier.weight(1f))
//
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth(),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Spacer(modifier = Modifier.weight(1f))
//                        Box(
//                            modifier = Modifier
//                                .shadow(
//                                    elevation = 10.dp,
//                                    shape = RoundedCornerShape(20.dp),
//                                    clip = false
//                                )
//                                .background(Color.White)
//                                .size(screenWidth * 0.2f)
//                        )
//                        Spacer(modifier = Modifier.weight(1f))
//                        Box(
//                            modifier = Modifier
//                                .shadow(
//                                    elevation = 10.dp,
//                                    shape = RoundedCornerShape(20.dp),
//                                    clip = false
//                                )
//                                .background(Color.White)
//                                .size(screenWidth * 0.2f)
//                        )
//                        Spacer(modifier = Modifier.weight(1f))
//                        Box(
//                            modifier = Modifier
//                                .shadow(
//                                    elevation = 10.dp,
//                                    shape = RoundedCornerShape(20.dp),
//                                    clip = false
//                                )
//                                .background(Color.White)
//                                .size(screenWidth * 0.2f)
//                        )
//                        Spacer(modifier = Modifier.weight(1f))
//                    }
//
//                    Spacer(modifier = Modifier.weight(1f))
//
//                    Box(
//                        modifier = Modifier
//                            .shadow(
//                                elevation = 10.dp,
//                                shape = RoundedCornerShape(20.dp),
//                                clip = false
//                            )
//                            .background(Color.White)
//                            .width(screenWidth * 0.8f)
//                            .height(screenHeight * 0.1f)
//                            .align(Alignment.CenterHorizontally)
//                    ) {
//                        Text(text = "No Achievement",
//                            modifier = Modifier
//                                .align(Alignment.Center))
//                    }
//
//                    Spacer(modifier = Modifier.weight(1f))
//
//
//                }
//            }
//
//        }
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

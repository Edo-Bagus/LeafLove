package com.example.leaflove

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController

@Composable
fun mainScreen(navHost: NavHostController, mainViewModel: MainViewModel, locViewModel: LocationViewModel){
    val image = painterResource(R.drawable.mainmenu)
    val image2 = painterResource(R.drawable.header)
    val image3 = painterResource(R.drawable.mainmenu2)
    val image4 = painterResource(R.drawable.footer)
    val columnsize = 420.dp
    val offsetmainmenu = 100.dp

    val location = locViewModel.locationState.observeAsState()
    val address = locViewModel.addressState.observeAsState("")

    val context = LocalContext.current
    val locationUtils = remember { LocationUtils(context) }


//    Box(modifier = Modifier
//        .fillMaxWidth()
//        .offset(y = -columnsize)
//        .zIndex(1.0f)){
//        Image(painter = image2, contentDescription = "", modifier = Modifier
//            .fillMaxSize())
//    }

    LaunchedEffect(key1 = true) {
        // Check for location permissions
        if (locationUtils.hasLocationPermission(context)) {
            // Permissions granted, request location updates
            locationUtils.requestLocationUpdate(locViewModel)
        } else {
            // Request location permissions
            requestLocationPermissions(context)
        }
    }

    Column(
        modifier = Modifier.offset(y = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
    ) {
        Text(text = mainViewModel.weatherState.value.weather?.firstOrNull()?.main ?: "No weather data")

        // Display the location coordinates
//        Text(text = "${location}")
        location.value?.let {
            Text(text = "Location: ${it.latitude}, ${it.longitude}")
        }

        // Display the address
        Text(text = "Address: ${address.value}")
    }



//    Box(modifier = Modifier
//        .fillMaxWidth()
//        .height(500.dp)
//        .offset(y = 410.dp)
//        .zIndex(1f)){ Image(
//        painter = image3,
//        contentDescription = "",
//            modifier = Modifier.fillMaxSize()
//    )}

//    Box(modifier = Modifier
//        .fillMaxWidth()
//        .height(500.dp)
//        .offset(y = 700.dp)
//        .zIndex(1f)){ Image(
//        painter = image4,
//        contentDescription = "",
//        modifier = Modifier.fillMaxSize()
//    )}

//    Column {
//        Row{
//            Image(painter = image2, contentDescription = "", modifier = Modifier.fillMaxSize())
//        }
//        Row{
//            Box(modifier = Modifier
//                .fillMaxWidth()
//                .height(410.dp)
//                .offset(x = 0.dp)){
//
//                Image(painter = image, contentDescription = "", modifier = Modifier.fillMaxSize())
//            }
//        }
//        Row{
//
//        }
    }

private fun requestLocationPermissions(context: Context) {
    if (context is Activity) {
        // Check if the permission is not granted
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
        }
    }
}

const val REQUEST_LOCATION_PERMISSION = 1

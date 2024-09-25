package com.example.leaflove

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController

@Composable
fun mainScreen(navHost: NavHostController, weatherViewModel: WeatherViewModel, locViewModel: LocationViewModel) {
    val context = LocalContext.current
    val locationUtils = remember { LocationUtils(context, locViewModel) }
    val location = locViewModel.locationState.observeAsState()

    // Start location updates when the composable is entered
    LaunchedEffect(Unit) {
        if (locationUtils.hasLocationPermission(context)) {
            locationUtils.requestLocationUpdate()
        } else {
            requestLocationPermissions(context)
        }
    }

    // Observe location updates and fetch weather
    location.value?.let { loc ->
        LaunchedEffect(loc.latitude, loc.longitude) {
            weatherViewModel.fetchWeather(loc.latitude, loc.longitude)
        }
    }

    // Stop location updates when the composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            locationUtils.stopLocationUpdates()
        }
    }

    Column(
        modifier = Modifier.offset(y = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
    ) {
        Text(text = weatherViewModel.weatherState.value.weather?.firstOrNull()?.main ?: "No weather data")

        weatherViewModel.weatherState.value.sys?.let { sys ->
            Text(text = "Country: ${sys.country}")
        }

        location.value?.let {
            Text(text = "Location: ${it.latitude}, ${it.longitude}")
        }
    }
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

package com.example.leaflove

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import java.util.Locale

class LocationUtils(val context: Context) {

    private val _fusedLocationClient: FusedLocationProviderClient
            = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun requestLocationUpdate(viewModel: LocationViewModel) {
        if (!hasLocationPermission(context)) {
            Log.e("LocationUtils", "Location permissions are not granted.")
            return
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.lastLocation?.let {
                    Log.d("goblok", "Location received: ${it.latitude}, ${it.longitude}")
                    val location = LocationData(latitude = it.latitude, longitude = it.longitude)
                    viewModel.updateLocation(location)

                    // Get the human-readable address from the coordinates
                    val address = reverseGeocodeLocation(location)
                    viewModel.updateAddress(address)
                }
            }
        }
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()
        _fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    fun hasLocationPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(context,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context,
            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    fun reverseGeocodeLocation(location: LocationData): String {
        return try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val coordinate = LatLng(location.latitude, location.longitude)
            val addresses: MutableList<Address>? =
                geocoder.getFromLocation(coordinate.latitude, coordinate.longitude, 1)

            if (addresses?.isNotEmpty() == true) {
                addresses[0].getAddressLine(0) ?: "Address not found"
            } else {
                "Address not found"
            }
        } catch (e: Exception) {
            Log.e("LocationUtils", "Geocoding failed: ${e.message}")
            "Geocoding error"
        }
    }
}
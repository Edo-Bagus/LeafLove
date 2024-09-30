package com.example.leaflove.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leaflove.data.LocationData

class LocationViewModel : ViewModel() {
    private val _locationState = mutableStateOf(LocationData(7.21312, 14.3213))
    val locationState: State<LocationData> = _locationState

    private val _addressState = MutableLiveData<String>()
    val addressState: LiveData<String> get() = _addressState

    fun updateLocation(locationData: LocationData) {
        _locationState.value = locationData
    }

    fun updateAddress(address: String) {
        _addressState.value = address
    }
}

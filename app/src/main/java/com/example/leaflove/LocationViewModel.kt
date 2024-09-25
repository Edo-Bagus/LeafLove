package com.example.leaflove

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationViewModel : ViewModel() {
    private val _locationState = MutableLiveData<LocationData>()
    val locationState: LiveData<LocationData> get() = _locationState

    private val _addressState = MutableLiveData<String>()
    val addressState: LiveData<String> get() = _addressState

    fun updateLocation(locationData: LocationData) {
        _locationState.value = locationData
    }

    fun updateAddress(address: String) {
        _addressState.value = address
    }
}

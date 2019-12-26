package com.example.mapfitlerapp.ui

import androidx.lifecycle.ViewModel
import com.example.mapfitlerapp.data.FilteredPinProvider

class MapFilterViewModel(private val pinProvider: FilteredPinProvider): ViewModel() {
    val services = pinProvider.services
    val filteredServices = pinProvider.filteredServices
    fun addServiceFilter(service: String) { pinProvider.addServiceFilter(service) }
    fun removeServiceFilter(service: String) { pinProvider.removeServiceFilter(service) }
}
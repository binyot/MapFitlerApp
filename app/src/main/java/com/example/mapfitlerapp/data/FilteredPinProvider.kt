package com.example.mapfitlerapp.data

interface FilteredPinProvider: PinProvider {
    fun addServiceFilter(service: String)
    fun removeServiceFilter(service: String)
    val filteredServices: Set<String>
}
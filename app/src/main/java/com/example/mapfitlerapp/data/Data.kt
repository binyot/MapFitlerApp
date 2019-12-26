package com.example.mapfitlerapp.data

data class Coordinates(val lat: Double, val lng: Double)
data class Pin(val id: Int, val service: String, val coordinates: Coordinates)
data class PinData(val services: List<String>, val pins: List<Pin>)
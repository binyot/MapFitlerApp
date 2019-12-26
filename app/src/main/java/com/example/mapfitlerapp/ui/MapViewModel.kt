package com.example.mapfitlerapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapfitlerapp.data.PinProvider
import kotlinx.coroutines.launch

class MapViewModel(private val pinProvider: PinProvider): ViewModel() {
    val pins = pinProvider.pins

    fun updatePins(context: Context) {
        viewModelScope.launch {
            pinProvider.update(context)
        }
    }
}
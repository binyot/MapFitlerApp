package com.example.mapfitlerapp.data

import android.content.Context
import androidx.lifecycle.LiveData

interface PinProvider {
    val pins: LiveData<List<Pin>>
    val services: LiveData<List<String>>
    suspend fun update(context: Context)
}
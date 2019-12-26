package com.example.mapfitlerapp.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap

class PinProviderImpl : FilteredPinProvider {
    private val mLivePins = MutableLiveData<List<Pin>>()
    override val pins: LiveData<List<Pin>> = mLivePins

    private var mServices = ConcurrentHashMap<String, ArrayList<Pin>>()
    private val mLiveServices = MutableLiveData<List<String>>()

    override val services: LiveData<List<String>> = mLiveServices

    override val filteredServices = LinkedHashSet<String>()

    override suspend fun update(context: Context) {
        withContext(Dispatchers.IO) {
            val mapper = jacksonObjectMapper()
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            val file = context.applicationContext.assets.open("pins.json")
            val pinData: PinData? = try {
                mapper.readValue(file)
            } catch (e: JsonMappingException) {
                // Показать ошибку?
                null
            }
            pinData?.pins?.forEach {
                mServices[it.service]?.add(it) ?: mServices.put(it.service, arrayListOf(it))
            }
            filteredServices.clear()
            mLiveServices.postValue(mServices.keys.toList())
            updateLivePins()
        }
    }

    override fun addServiceFilter(service: String) {
        filteredServices.add(service)
        updateLivePins()
    }

    override fun removeServiceFilter(service: String) {
        filteredServices.remove(service)
        updateLivePins()
    }

    private fun updateLivePins() {
        mLivePins.postValue(
            mServices
                .filter { !filteredServices.contains(it.key) }
                .map { it.value }
                .flatten()
        )
    }
}
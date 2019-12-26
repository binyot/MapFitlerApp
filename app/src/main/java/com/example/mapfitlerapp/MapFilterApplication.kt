package com.example.mapfitlerapp

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.example.mapfitlerapp.data.FilteredPinProvider
import com.example.mapfitlerapp.data.PinProvider
import com.example.mapfitlerapp.data.PinProviderImpl
import com.example.mapfitlerapp.ui.MapFilterViewModel
import com.example.mapfitlerapp.ui.MapViewModel
import com.example.mapfitlerapp.util.ViewModelFactory
import com.example.mapfitlerapp.util.bindViewModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class MapFilterApplication: Application(), KodeinAware {
    override val kodein = Kodein {
        import(androidXModule(this@MapFilterApplication))

        bind<FilteredPinProvider>() with singleton { PinProviderImpl() }

        bind<ViewModelProvider.Factory>() with singleton {
            ViewModelFactory(kodein.direct)
        }
        bindViewModel<MapViewModel>() with singleton { MapViewModel(instance()) }
        bindViewModel<MapFilterViewModel>() with singleton { MapFilterViewModel(instance()) }
    }
}
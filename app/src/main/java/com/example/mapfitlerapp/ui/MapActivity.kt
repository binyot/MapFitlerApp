package com.example.mapfitlerapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.example.mapfitlerapp.R
import com.example.mapfitlerapp.util.viewModel
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

class MapActivity : AppCompatActivity(), OnMapReadyCallback, KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val mViewModel: MapViewModel by viewModel()

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_map)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mViewModel.updatePins(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.map_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.entryFilter -> {
                val intent = Intent(this, MapFilterActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val moscow = LatLng(55.7, 37.6)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(moscow, 10.0f))

        mViewModel.pins.observe(this, Observer {
            mMap.clear()
            it.forEach {
                val position = LatLng(it.coordinates.lat, it.coordinates.lng)
                mMap.addMarker(MarkerOptions().position(position).title("${it.id}(${it.service})"))
            }
        })
    }
}

package com.example.mapfitlerapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mapfitlerapp.R
import com.example.mapfitlerapp.util.viewModel
import kotlinx.android.synthetic.main.activity_map_filter.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

class MapFilterActivity : AppCompatActivity(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val mViewModel: MapFilterViewModel by viewModel()
    private val serviceAdapter = ServiceAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_filter)

        mViewModel.services.observe(this, Observer {
            serviceAdapter.serviceList = it
            serviceAdapter.setChecked(mViewModel.filteredServices.toList())
        })

        serviceAdapter.onServiceChecked = { service, checked ->  
            if (checked) {
                mViewModel.addServiceFilter(service)
            } else {
                mViewModel.removeServiceFilter(service)
            }
        }

        with (recyclerView) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = serviceAdapter
        }
    }
}

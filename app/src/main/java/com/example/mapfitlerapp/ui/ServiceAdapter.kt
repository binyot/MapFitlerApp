package com.example.mapfitlerapp.ui

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.set
import androidx.recyclerview.widget.RecyclerView
import com.example.mapfitlerapp.R
import kotlinx.android.synthetic.main.service_item.view.*

class ServiceAdapter: RecyclerView.Adapter<ServiceAdapter.ViewHolder>() {

    private val checked = SparseBooleanArray()

    var onServiceChecked: ((service: String, checked: Boolean) -> Unit)? = null

    var serviceList: List<String> = ArrayList()
        set(serviceList) {
            field = serviceList
            // TODO: здесь может быть DiffUtil
            checked.clear()
            notifyDataSetChanged()
        }

    fun setChecked(services: List<String>) {
        checked.clear()
        services.forEach {
            val idx = serviceList.indexOf(it)
            checked[idx] = true
        }
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val service = serviceList[position]
        val isChecked = checked[position]
        holder.bind(service, isChecked)
        holder.itemView.checkBox.setOnClickListener {
            checked[position] = it.checkBox.isChecked
            onServiceChecked?.invoke(service, checked[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.service_item, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        fun bind(service: String, isChecked: Boolean) {
            view.checkBox.text = service
            view.checkBox.isChecked = isChecked
        }
    }
}
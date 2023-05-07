package com.example.sltb_bus.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sltb_bus.Models.BusModel
import com.example.sltb_bus.R


class TogoBusAdapter() : RecyclerView.Adapter<TogoBusAdapter.ViewHolder>() {
    lateinit var data: List<BusModel>
    lateinit var context: Context
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val Select: CheckBox
        val StartTime :TextView
        val ivDelete: ImageView
        val BusNumber :TextView
        val EndTime :TextView
        val startLocation :TextView
        val EndLocation :TextView
        val date :TextView

        init {
            Select = view.findViewById(R.id.cbCheckbox)
            StartTime = view.findViewById(R.id.tvStartTime)
            EndTime = view.findViewById(R.id.tvEndTime)
            startLocation = view.findViewById(R.id.tvStartLocation)
            EndLocation = view.findViewById(R.id.tvEndLocation)
            BusNumber = view.findViewById(R.id.BusNumber)
            date = view.findViewById(R.id.date)
            ivDelete = view.findViewById(R.id.ivDelete)
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TogoBusAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bus_to_go,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TogoBusAdapter.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int =data.size
}

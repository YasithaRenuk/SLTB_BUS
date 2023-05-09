package com.example.sltb_bus.Adapter

import BusModel
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sltb_bus.R
import com.google.firebase.database.DatabaseReference

class BookingbusSerchAdapter() : RecyclerView.Adapter<BookingbusSerchAdapter.ViewHolder>()  {
    private var data: List<BusModel> = emptyList()
    lateinit var context: Context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val Select: CheckBox
        val StartTime : TextView
        val BusNumber : TextView
        val EndTime : TextView
        val tvNoofSetes : TextView
        val tvbookedseates : TextView
        val date : TextView

        init {
            Select = view.findViewById(R.id.cbCheckbox)
            StartTime = view.findViewById(R.id.tvStartTime)
            EndTime = view.findViewById(R.id.tvEndTime)
            tvNoofSetes = view.findViewById(R.id.tvNoofSetes)
            tvbookedseates = view.findViewById(R.id.tvbookedseates)
            BusNumber = view.findViewById(R.id.BusNumber)
            date = view.findViewById(R.id.date)
        }

    }
    fun setData(data: List<BusModel>, context: Context) {
        this.data = data
        this.context = context
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingbusSerchAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.buss,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingbusSerchAdapter.ViewHolder, position: Int) {
        holder.BusNumber.text = data[position].BusNumbr
        holder.StartTime.text = data[position].StartTime
        holder.EndTime.text = data[position].EndTime
        holder.tvNoofSetes.text = data[position].NoFoSeat.toString()
        holder.tvbookedseates.text = data[position].NoFoBookingSeat.toString()
        holder.date.text = data[position].Date

    }

    override fun getItemCount(): Int =data.size
}

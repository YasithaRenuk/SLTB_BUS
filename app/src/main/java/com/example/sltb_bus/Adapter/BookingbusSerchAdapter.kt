package com.example.sltb_bus.Adapter

import BusModel
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.sltb_bus.Driver.DriverProfileViewActivty
import com.example.sltb_bus.R
import com.example.sltb_bus.SeatBookingActivity
import com.google.firebase.database.DatabaseReference

class BookingbusSerchAdapter() : RecyclerView.Adapter<BookingbusSerchAdapter.ViewHolder>()  {
    private var data: List<BusModel> = emptyList()
    lateinit var context: Context
    private var pemail:String = ""

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val Select: CheckBox
        val StartTime : TextView
        val BusNumber : TextView
        val EndTime : TextView
        val tvNoofSetes : TextView
        val tvbookedseates : TextView
        val date : TextView
        val go: ImageView

        init {
            Select = view.findViewById(R.id.cbCheckbox)
            StartTime = view.findViewById(R.id.tvStartTime)
            EndTime = view.findViewById(R.id.tvEndTime)
            tvNoofSetes = view.findViewById(R.id.tvNoofSetes)
            tvbookedseates = view.findViewById(R.id.tvbookedseates)
            BusNumber = view.findViewById(R.id.BusNumber)
            date = view.findViewById(R.id.date)
            go = view.findViewById(R.id.ivgo)
        }

    }
    fun setData(data: List<BusModel>, context: Context,pemail:String) {
        this.data = data
        this.context = context
        this.pemail = pemail
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
        holder.go.setOnClickListener {
            if(holder.Select.isChecked){
                val intent = Intent(context, SeatBookingActivity::class.java)
                intent.putExtra("pEmail",pemail)
                intent.putExtra("BusId",data[position].BusID)
                intent.putExtra("nofoseats",data[position].NoFoSeat!!)
                intent.putExtra("bookedseat",data[position].NoFoBookingSeat!!)
                intent.putExtra("BusNumbr",data[position].BusNumbr)
                intent.putExtra("DEmail",data[position].DEmail)
                intent.putExtra("StartLocation",data[position].StartLocation)
                intent.putExtra("EndLocation",data[position].EndLocation)
                intent.putExtra("StartTime",data[position].StartTime)
                intent.putExtra("EndTime",data[position].EndTime)
                intent.putExtra("Date",data[position].Date)
                context.startActivity(intent)
            }
        }

    }

    override fun getItemCount(): Int =data.size
}

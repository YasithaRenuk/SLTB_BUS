package com.example.sltb_bus.Adapter

import BusModel
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sltb_bus.R
import com.example.sltb_bus.UpdateBookedtecat
import com.example.sltb_bus.UpdateBusActivity
import com.example.sltb_bus.UserTypeActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class TogoBusAdapter() : RecyclerView.Adapter<TogoBusAdapter.ViewHolder>() {
    private var data: List<BusModel> = emptyList()
    lateinit var context: Context
    private lateinit var dbRef: DatabaseReference
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val Select: CheckBox
        val StartTime :TextView
        val ivDelete: ImageView
        val BusNumber :TextView
        val EndTime :TextView
        val startLocation :TextView
        val EndLocation :TextView
        val date :TextView
        val update:ImageView

        init {
            Select = view.findViewById(R.id.cbCheckbox)
            StartTime = view.findViewById(R.id.tvStartTime)
            EndTime = view.findViewById(R.id.tvEndTime)
            startLocation = view.findViewById(R.id.tvStartLocation)
            EndLocation = view.findViewById(R.id.tvEndLocation)
            BusNumber = view.findViewById(R.id.BusNumber)
            date = view.findViewById(R.id.date)
            ivDelete = view.findViewById(R.id.ivDelete)
            update = view.findViewById(R.id.ivupdate)
        }

    }
    fun setData(data: List<BusModel>, context: Context) {
        this.data = data
        this.context = context
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TogoBusAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bus_to_go,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TogoBusAdapter.ViewHolder, position: Int) {
        holder.BusNumber.text = data[position].BusNumbr
        holder.StartTime.text = data[position].StartTime
        holder.EndTime.text = data[position].EndTime
        holder.startLocation.text = data[position].StartLocation
        holder.EndLocation.text = data[position].EndLocation
        holder.date.text = data[position].Date
        holder.ivDelete.setOnClickListener {
            if(holder.Select.isChecked){
                dbRef = FirebaseDatabase.getInstance().getReference("Bus")
                dbRef.child(data[position].BusID!!).removeValue().addOnSuccessListener {
                    Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_LONG).show()
                    setData(data, context)
                }.addOnFailureListener {
                    Toast.makeText(context, "Failed Deleted", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(context,"Cannot delete unmarked Todo items", Toast.LENGTH_LONG).show()
            }
        }
        holder.update.setOnClickListener {
            if(holder.Select.isChecked){
                val intent = Intent(context, UpdateBusActivity::class.java)
                intent.putExtra("busID",data[position].BusID)
                intent.putExtra("busNumbr",data[position].BusNumbr)
                intent.putExtra("demail",data[position].DEmail)
                intent.putExtra("startLocation",data[position].StartLocation)
                intent.putExtra("endLocation",data[position].EndLocation)
                intent.putExtra("startTime",data[position].StartTime)
                intent.putExtra("endTime",data[position].EndTime)
                intent.putExtra("date",data[position].Date)
                intent.putExtra("noFoSeat",data[position].NoFoSeat!!.toInt())
                intent.putExtra("noFoBookingSeat",data[position].NoFoBookingSeat)
                context.startActivity(intent)
            }else{
                Toast.makeText(context,"Cannot delete unmarked Todo items", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun getItemCount(): Int =data.size
}

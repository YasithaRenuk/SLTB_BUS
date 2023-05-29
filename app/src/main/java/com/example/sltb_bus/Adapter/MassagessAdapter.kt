package com.example.sltb_bus.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sltb_bus.Models.MassageModel
import com.example.sltb_bus.R
import com.example.sltb_bus.UpdateBusActivity
import com.example.sltb_bus.UpdateMassagesActivty
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MassagessAdapter() : RecyclerView.Adapter<MassagessAdapter.ViewHolder>() {
    private var data: List<MassageModel> = emptyList()
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
        val go :ImageView

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
            go = view.findViewById(R.id.ivgo)
        }

    }
    fun setData(data: List<MassageModel>, context: Context) {
        this.data = data
        this.context = context
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MassagessAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bus_to_go,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MassagessAdapter.ViewHolder, position: Int) {
        holder.go.setImageBitmap(null)
        holder.BusNumber.text = ""
        holder.StartTime.text = data[position].Massage
        holder.EndTime.text=""
        holder.startLocation.text = ""
        holder.EndLocation.text = ""
        holder.date.text = data[position].Date
        holder.ivDelete.setOnClickListener {
            if(holder.Select.isChecked){
                dbRef = FirebaseDatabase.getInstance().getReference("Massages")
                dbRef.child(data[position].MsqID!!).removeValue().addOnSuccessListener {
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
                val intent = Intent(context, UpdateMassagesActivty::class.java)
                intent.putExtra("MsqID",data[position].MsqID)
                intent.putExtra("Massage",data[position].Massage)
                intent.putExtra("Date",data[position].Date)
                context.startActivity(intent)
            }else{
                Toast.makeText(context,"Cannot Update unmarked Todo items", Toast.LENGTH_LONG).show()
            }
        }

        }

        override fun getItemCount(): Int =data.size
    }

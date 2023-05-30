package com.example.sltb_bus.Adapter

import BusModel
import android.annotation.SuppressLint
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
import com.example.sltb_bus.Models.BookModel
import com.example.sltb_bus.R
import com.example.sltb_bus.UpdateBookedtecat
import com.google.firebase.database.*

class BookBusAdapter() : RecyclerView.Adapter<BookBusAdapter.ViewHolder>()  {
    private var data: List<BookModel> = emptyList()
    lateinit var context: Context
    private lateinit var dbRef: DatabaseReference
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val Select: CheckBox
        val StartTime : TextView
        val ivDelete: ImageView
        val BusNumber : TextView
        val EndTime : TextView
        val startLocation : TextView
        val EndLocation : TextView
        val Nofobookedseat :TextView
        val date : TextView
        val ivupdate : ImageView

        init {
            Select = view.findViewById(R.id.cbCheckbox)
            StartTime = view.findViewById(R.id.tvStartTime)
            EndTime = view.findViewById(R.id.tvEndTime)
            startLocation = view.findViewById(R.id.tvStartLocation)
            EndLocation = view.findViewById(R.id.tvEndLocation)
            BusNumber = view.findViewById(R.id.BusNumber)
            date = view.findViewById(R.id.date)
            Nofobookedseat=view.findViewById(R.id.tvBookNumber)
            ivDelete = view.findViewById(R.id.ivDelete)
            ivupdate = view.findViewById(R.id.ivupdate)
        }

    }
    fun setData(data: List<BookModel>, context: Context) {
        this.data = data
        this.context = context
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookBusAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bookbus,parent,false)
        return ViewHolder(view)
    }


    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: BookBusAdapter.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        dbRef = FirebaseDatabase.getInstance().getReference("Bus")
        val checkQuery = dbRef.child(data[position].busId.toString())
            checkQuery.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val bus = dataSnapshot.getValue(BusModel::class.java)
                holder.BusNumber.text = bus?.BusNumbr.toString()
                holder.StartTime.text = bus?.StartTime.toString()
                holder.EndTime.text = bus?.EndTime.toString()
                holder.startLocation.text = bus?.StartLocation.toString()
                holder.EndLocation.text = bus?.EndLocation.toString()
                holder.date.text = bus?.Date.toString()
                holder.Nofobookedseat.text = data[position].noFoSeat.toString()
                holder.ivDelete.setOnClickListener {
                    if(holder.Select.isChecked){
                        val Bus = HashMap<String,Any>()
                        Bus.put("busID",bus?.BusID.toString())
                        Bus.put("busNumbr",bus?.BusNumbr.toString())
                        Bus.put("demail",bus?.DEmail.toString())
                        Bus.put("startLocation",bus?.StartLocation.toString())
                        Bus.put("endLocation",bus?.EndLocation.toString())
                        Bus.put("startTime",bus?.StartTime.toString())
                        Bus.put("endTime",bus?.EndTime.toString())
                        Bus.put("date",bus?.Date.toString())
                        Bus.put("noFoSeat",bus?.NoFoSeat.toString().toInt())
                        Bus.put("noFoBookingSeat",bus?.NoFoBookingSeat.toString().toInt()-data[position].noFoSeat.toString().toInt())
                        dbRef = FirebaseDatabase.getInstance().getReference("Bus")
                        val cheak = dbRef.child(bus?.BusID.toString()).updateChildren(Bus).addOnCompleteListener {
                            if(it.isSuccessful){
                                    updatebooked(data[position].bookId.toString())
                            }
                        } .addOnFailureListener { err ->
                                Toast.makeText(context, "Error ${err.message}", Toast.LENGTH_LONG).show()
                        }
                    }else{
                        Toast.makeText(context,"Cannot delete unmarked Todo items", Toast.LENGTH_LONG).show()
                    }
                }
                holder.ivupdate.setOnClickListener {
                    if(holder.Select.isChecked){
                        val intent = Intent(context,UpdateBookedtecat::class.java)
                        intent.putExtra("busID",bus?.BusID.toString())
                        intent.putExtra("busNumbr",bus?.BusNumbr.toString())
                        intent.putExtra("demail",bus?.DEmail.toString())
                        intent.putExtra("startLocation",bus?.StartLocation.toString())
                        intent.putExtra("endLocation",bus?.EndLocation.toString())
                        intent.putExtra("startTime",bus?.StartTime.toString())
                        intent.putExtra("endTime",bus?.EndTime.toString())
                        intent.putExtra("date",bus?.Date.toString())
                        intent.putExtra("noFoSeat",bus?.NoFoSeat.toString().toInt())
                        intent.putExtra("noFoBookingSeat",data[position].noFoSeat.toString())
                        intent.putExtra("exenoFoBookingSeat",bus?.NoFoBookingSeat.toString())
                        intent.putExtra("bookId",data[position].bookId.toString())
                        intent.putExtra("pemail",data[position].pemail.toString())
                        context.startActivity(intent)
                    }else{
                        Toast.makeText(context,"Cannot delete unmarked Todo items", Toast.LENGTH_LONG).show()
                    }
                }
            }override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    override fun getItemCount(): Int =data.size

    private fun updatebooked(BookID:String){
        dbRef = FirebaseDatabase.getInstance().getReference("Book")
        dbRef.child(BookID).removeValue()
            .addOnCompleteListener {
                Toast.makeText(context, "Data inserted successfully", Toast.LENGTH_LONG).show()

            }.addOnFailureListener { err ->
                Toast.makeText(context, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }

}

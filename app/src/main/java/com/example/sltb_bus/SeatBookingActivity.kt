package com.example.sltb_bus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.sltb_bus.Models.BookModel
import com.example.sltb_bus.Passenger.PassengerDashBordActivtiy
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SeatBookingActivity : AppCompatActivity() {

    private lateinit var seatnumbers : EditText
    private lateinit var bbusNumber : TextView
    private lateinit var btvStartLocation : TextView
    private lateinit var btvEndLocation : TextView
    private lateinit var btvStartTimetime : TextView
    private lateinit var btvEndTime : TextView
    private lateinit var btvNoofSetes : TextView
    private lateinit var btvNoFobooked : TextView
    private lateinit var btnbook : Button
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat_booking)
        val pemail = intent.extras!!.getString("pEmail")
        val BusId = intent.extras!!.getString("BusId")
        val nofoseats = intent.extras!!.getInt("nofoseats")
        val bookedseat = intent.extras!!.getInt("bookedseat")
        val BusNumbr = intent.extras!!.getString("BusNumbr")
        val DEmail = intent.extras!!.getString("DEmail")
        val StartLocation = intent.extras!!.getString("StartLocation")
        val EndLocation = intent.extras!!.getString("EndLocation")
        val StartTime = intent.extras!!.getString("StartTime")
        val EndTime = intent.extras!!.getString("EndTime")
        val Date = intent.extras!!.getString("Date")

        seatnumbers = findViewById(R.id.seatnumbers)
        bbusNumber = findViewById(R.id.busNumber)
        btvStartLocation = findViewById(R.id.tvStartLocation)
        btvEndLocation = findViewById(R.id.tvEndLocation)
        btvStartTimetime = findViewById(R.id.tvStartTimetime)
        btvEndTime = findViewById(R.id.tvEndTime)
        btvNoofSetes = findViewById(R.id.tvNoofSetes)
        btvNoFobooked = findViewById(R.id.tvNoFobooked)
        btnbook = findViewById(R.id.btnbook)

        bbusNumber.setText(BusNumbr)
        btvStartLocation.setText(StartLocation)
        btvEndLocation.setText(EndLocation)
        btvStartTimetime.setText(StartTime)
        btvNoFobooked.setText(bookedseat.toString())
        btvNoofSetes.setText(nofoseats.toString())
        btvEndTime.setText(EndTime)

        btnbook.setOnClickListener {
            val nofofseat = seatnumbers.text.toString().toInt()
            if(canbecheker(nofofseat,bookedseat!!.toInt(),nofoseats!!.toInt())){
                val Bus = HashMap<String,Any>()
                Bus.put("busID",BusId!!)
                Bus.put("busNumbr",BusNumbr!!)
                Bus.put("demail",DEmail!!)
                Bus.put("startLocation",StartLocation!!)
                Bus.put("endLocation",EndLocation!!)
                Bus.put("startTime",StartTime!!)
                Bus.put("endTime",EndTime!!)
                Bus.put("date",Date!!)
                Bus.put("noFoSeat",nofoseats!!.toInt())
                Bus.put("noFoBookingSeat",bookedseat!!.toInt()+nofofseat)
                dbRef = FirebaseDatabase.getInstance().getReference("Bus")
                val cheak = dbRef.child(BusId).updateChildren(Bus).addOnCompleteListener {
                    if(it.isSuccessful){
                      updatebooked(pemail!!,BusId,nofofseat)
                    }
                } .addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

    }
//checking seat availability function
    private fun canbecheker(amount :Int,booked:Int,have:Int):Boolean{
        if(amount>0){
            if((booked+amount)<=have){
                return true
            }else{
                Toast.makeText(this,"Not enough Seate left to get",Toast.LENGTH_LONG).show()
            }
        }
        return false
    }
//update seat bookings to data base
    private fun updatebooked(pemail:String,BusId:String,Nofoseat:Int){
        dbRef = FirebaseDatabase.getInstance().getReference("Book")
        val BookID = dbRef.push().key!!
        val book =  BookModel(BookID,pemail,BusId,Nofoseat)
        dbRef.child(BookID).setValue(book)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
                val intent = Intent(this, PassengerDashBordActivtiy::class.java)
                intent.putExtra("Email",pemail)
                startActivity(intent)

            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}
package com.example.sltb_bus

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.sltb_bus.Passenger.PassengerDashBordActivtiy
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateBookedtecat : AppCompatActivity() {

    private lateinit var BusNumber : TextView
    private lateinit var SeatNumber : EditText
    private lateinit var bupdate : Button
    private lateinit var dbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_bookedtecat)

        BusNumber = findViewById(R.id.tvbusnumber)
        SeatNumber = findViewById(R.id.seatnumbers)
        bupdate = findViewById(R.id.btupdate)

        val busID = intent.extras!!.getString("busID")
        val busNumbr = intent.extras!!.getString("busNumbr")
        val demail = intent.extras!!.getString("demail")
        val startLocation = intent.extras!!.getString("startLocation")
        val endLocation = intent.extras!!.getString("endLocation")
        val startTime = intent.extras!!.getString("startTime")
        val endTime = intent.extras!!.getString("endTime")
        val date = intent.extras!!.getString("date")
        val noFoSeat = intent.extras!!.getInt("noFoSeat")
        val noFoBookingSeat = intent.extras!!.getString("noFoBookingSeat")
        val bookId = intent.extras!!.getString("bookId")
        val pemail = intent.extras!!.getString("pemail")
        val exenoFoBookingSeat = intent.extras!!.getString("exenoFoBookingSeat")

        BusNumber.setText(busNumbr)
        bupdate.setOnClickListener {
            val Bus = HashMap<String,Any>()
            if (busID != null && busNumbr != null && demail != null && startLocation != null && endLocation != null && startTime != null && endTime != null && date != null && noFoSeat != null && bookId != null && pemail!= null) {
                Bus.put("busID", busID)
                Bus.put("busNumbr", busNumbr)
                Bus.put("demail", demail)
                Bus.put("startLocation", startLocation)
                Bus.put("endLocation", endLocation)
                Bus.put("startTime", startTime)
                Bus.put("endTime", endTime)
                Bus.put("date", date)
                Bus.put("noFoSeat", noFoSeat)
                Bus.put("noFoBookingSeat", (exenoFoBookingSeat!!.toInt() - noFoBookingSeat!!.toInt()) + SeatNumber.text.toString().toInt())
                dbRef = FirebaseDatabase.getInstance().getReference("Bus")
                val cheak = dbRef.child(busID).updateChildren(Bus).addOnCompleteListener {
                    if(it.isSuccessful){
                        val Book = HashMap<String,Any>()
                        Book.put("bookId",bookId)
                        Book.put("busId",busID)
                        Book.put("noFoSeat",SeatNumber.text.toString().toInt())
                        Book.put("pemail",pemail)
                        dbRef = FirebaseDatabase.getInstance().getReference("Book")
                        val cheak = dbRef.child(bookId).updateChildren(Book).addOnCompleteListener {
                            if(it.isSuccessful){
                                Toast.makeText(this, "Update Successfully", Toast.LENGTH_LONG).show()
                                val intent = Intent(this, PassengerDashBordActivtiy::class.java)
                                intent.putExtra("Email",pemail)
                                intent.putExtra("Type","Passengers")
                                startActivity(intent)
                            }
                        }.addOnFailureListener { err ->
                        Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                } .addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
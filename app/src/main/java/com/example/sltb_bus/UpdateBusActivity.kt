package com.example.sltb_bus

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.sltb_bus.Driver.DriverProfileViewActivty
import com.example.sltb_bus.Driver.MainActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateBusActivity : AppCompatActivity() {
    private lateinit var startl: EditText
    private lateinit var endl: EditText
    private lateinit var startt: EditText
    private lateinit var endt: EditText
    private lateinit var bdate: EditText
    private lateinit var nofoseat: EditText
    private  lateinit var busnumber: EditText
    private lateinit var btupdate : Button
    private lateinit var dbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_bus)

        startl = findViewById(R.id.etstlocation)
        endl = findViewById(R.id.etendlocation)
        startt = findViewById(R.id.etsttime)
        endt = findViewById(R.id.etendtime)
        bdate = findViewById(R.id.etdate)
        nofoseat = findViewById(R.id.etNoFoSeat)
        btupdate = findViewById(R.id.btnupdate)
        busnumber = findViewById(R.id.etbusNumber)

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

        startl.setText(startLocation)
        endl.setText(endLocation)
        startt.setText(startTime)
        endt.setText(endTime)
        bdate.setText(date)
        nofoseat.setText(noFoSeat.toString())
        busnumber.setText(busNumbr)

        btupdate.setOnClickListener {
            val Bus = HashMap<String,Any>()
            if (busID != null && demail!= null) {
                Bus.put("busID", busID)
                Bus.put("busNumbr", busnumber.text.toString())
                Bus.put("demail", demail)
                Bus.put("startLocation", startl.text.toString())
                Bus.put("endLocation", endl.text.toString())
                Bus.put("startTime", startt.text.toString())
                Bus.put("endTime", endt.text.toString())
                Bus.put("date", bdate.text.toString())
                Bus.put("noFoSeat", nofoseat.text.toString().toInt())
                dbRef = FirebaseDatabase.getInstance().getReference("Bus")
                val cheak = dbRef.child(busID).updateChildren(Bus).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(this, "Update Successfully", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("Email",demail)
                        intent.putExtra("Type","Driver")
                        startActivity(intent)
                    }
                }
            }
        }

    }
}
package com.example.sltb_bus

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.sltb_bus.Driver.DriverProfileViewActivty

class PassengerDashBordActivtiy : AppCompatActivity() {

    private lateinit var btprofilview : Button
    private lateinit var btbook: Button
    private lateinit var btchekbooking : Button
    private lateinit var btnContactus:Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passenger_dash_bord_activtiy)

        btprofilview =findViewById(R.id.btnViewProfile)
        btbook = findViewById(R.id.btnBooking)
        btchekbooking = findViewById(R.id.btnTimeTable)
        btnContactus = findViewById(R.id.btnContactus)

        val email = intent.extras!!.getString("Email")

        btprofilview.setOnClickListener {
            val intent = Intent(this, DriverProfileViewActivty::class.java)
            intent.putExtra("Email",email)
            intent.putExtra("Type","Passenger")
            startActivity(intent)
        }

        btnContactus.setOnClickListener {
            val intent = Intent(this, AboutActivety::class.java)
            startActivity(intent)
        }

    }
}
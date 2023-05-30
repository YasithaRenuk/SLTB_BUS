package com.example.sltb_bus.Passenger

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.sltb_bus.AboutActivety
import com.example.sltb_bus.BookingCheckActivity
import com.example.sltb_bus.Driver.DriverProfileViewActivty
import com.example.sltb_bus.R
import com.example.sltb_bus.cheking

class PassengerDashBordActivtiy : AppCompatActivity() {

    private lateinit var btprofilview : Button
    private lateinit var btbook: Button
    private lateinit var btchekbooking : Button
    private lateinit var btnContactus:Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passenger_dash_bord_activtiy)
//dashboard connecting buttons
        btprofilview =findViewById(R.id.btnViewProfile)
        btbook = findViewById(R.id.btnBooking)
        btchekbooking = findViewById(R.id.btnTimeTable)
        btnContactus = findViewById(R.id.btnContactus)

        val email = intent.extras!!.getString("Email")
//dashboard view profile
        btprofilview.setOnClickListener {
            val intent = Intent(this, DriverProfileViewActivty::class.java)
            intent.putExtra("Email",email)
            intent.putExtra("Type","Passenger")
            startActivity(intent)
        }
//dashboard contact us
        btnContactus.setOnClickListener {
            val intent = Intent(this, AboutActivety::class.java)
            startActivity(intent)
        }
//dashboard booking
        btbook.setOnClickListener {
            val intent = Intent(this, cheking::class.java)
            intent.putExtra("Email",email)
            startActivity(intent)
        }
//dashboard timetable
        btchekbooking.setOnClickListener {
            val intent = Intent(this, BookingCheckActivity::class.java)
            intent.putExtra("Email",email)
            startActivity(intent)
        }

    }
}
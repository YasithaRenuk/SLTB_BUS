package com.example.sltb_bus.Driver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.sltb_bus.R

class MainActivity : AppCompatActivity() {

    private lateinit var btprofilview : Button
    private lateinit var btAddnewbus :Button
    private lateinit var bttrakingtrn :Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btprofilview =findViewById(R.id.btnViewProfile)
        btAddnewbus = findViewById(R.id.btnSavebus)
        bttrakingtrn = findViewById(R.id.btnStartTern)

        val email = intent.extras!!.getString("Email")

        btprofilview.setOnClickListener {
            val intent = Intent(this, DriverProfileViewActivty::class.java)
            intent.putExtra("Email",email)
            intent.putExtra("Type","Driver")
            startActivity(intent)
        }

        btAddnewbus.setOnClickListener {
            val intent = Intent(this, AddBusActivity::class.java)
            intent.putExtra("Email",email)
            startActivity(intent)
        }

        bttrakingtrn.setOnClickListener {
            val intent = Intent(this, StartTurn::class.java)
            intent.putExtra("Email",email)
            startActivity(intent)
        }
    }
}
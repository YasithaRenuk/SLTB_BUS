package com.example.sltb_bus

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.sltb_bus.Driver.MainActivity

class UserTypeActivity : AppCompatActivity() {

    private  lateinit var  btDriver:Button
    private  lateinit var  btPassenger:Button
    private  lateinit var  btnAdmin:Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_type)

        btDriver = findViewById(R.id.btnDriver)
        btPassenger = findViewById(R.id.btnPassenger)
        btnAdmin = findViewById(R.id.btnAdmin)

        btDriver.setOnClickListener {
            val intent = Intent(this, DLoginActivity::class.java)
            intent.putExtra("Type","Driver")
            startActivity(intent)
        }

        btPassenger.setOnClickListener {
            val intent = Intent(this, DLoginActivity::class.java)
            intent.putExtra("Type","Passenger")
            startActivity(intent)
        }

        btnAdmin.setOnClickListener {
            val intent = Intent(this, DLoginActivity::class.java)
            intent.putExtra("Type","Admin")
            startActivity(intent)
        }

    }
}
package com.example.sltb_bus

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AdminDashbordActivty : AppCompatActivity() {

    private lateinit var btnContactus:Button
    private lateinit var btAddMassages:Button
    private lateinit var btnAddBus:Button
    private lateinit var btnTimeTable:Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashbord_activty)

        btnContactus = findViewById(R.id.btnContactus)
        btnAddBus = findViewById(R.id.btnAddBus)
        btAddMassages = findViewById(R.id.btAddMassages)
        btnTimeTable = findViewById(R.id.btnTimeTable)

        btnContactus.setOnClickListener {
            val intent = Intent(this,AboutActivety::class.java)
            startActivity(intent)
        }

        btnTimeTable.setOnClickListener {
            val intent = Intent(this,SeeAllAddBussActivty::class.java)
            startActivity(intent)
        }
    }
}
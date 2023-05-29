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
    private lateinit var btnadd:Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashbord_activty)

        btnContactus = findViewById(R.id.btnContactus)
        btnAddBus = findViewById(R.id.btnAddBus)
        btAddMassages = findViewById(R.id.btAddMassages)
        btnTimeTable = findViewById(R.id.btnTimeTable)
        btnadd = findViewById(R.id.btnAdd)

        btnContactus.setOnClickListener {
            val intent = Intent(this,AboutActivety::class.java)
            startActivity(intent)
        }

        btnTimeTable.setOnClickListener {
            val intent = Intent(this,SeeAllAddBussActivty::class.java)
            startActivity(intent)
        }

        btnAddBus.setOnClickListener {
            val intent = Intent(this,AddbusAdminActivity::class.java)
            startActivity(intent)
        }

        btAddMassages.setOnClickListener {
            val intent = Intent(this,ManageMassagesActivity::class.java)
            startActivity(intent)
        }

        btnadd.setOnClickListener {
            val intent = Intent(this,AddMassagesActivty::class.java)
            startActivity(intent)
        }
    }
}
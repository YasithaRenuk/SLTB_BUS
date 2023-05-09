package com.example.sltb_bus.Driver

import BusModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sltb_bus.Adapter.TogoBusAdapter
import com.example.sltb_bus.R
import com.google.firebase.database.*

class StartTurn : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_turn)
        val recyclerView: RecyclerView = findViewById(R.id.rcBus)

        val ui = this
        val adapter = TogoBusAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(ui)
        val email = intent.extras!!.getString("Email")

        dbRef = FirebaseDatabase.getInstance().getReference("Bus")
        val checkQuery = dbRef.orderByChild("demail").equalTo(email)
        checkQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val tempList = ArrayList<BusModel>()
                for (snapshot in dataSnapshot.children) {
                    val bus = snapshot.getValue(BusModel::class.java)
                    bus?.let { tempList.add(it) }
                }
                adapter.setData(tempList, ui)
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }
}
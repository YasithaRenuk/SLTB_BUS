package com.example.sltb_bus

import BusModel
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sltb_bus.Adapter.TogoBusAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SeeAllAddBussActivty : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_all_add_buss_activty)
        val recyclerView: RecyclerView = findViewById(R.id.rcBus)

        val ui = this
        val adapter = TogoBusAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(ui)

        dbRef = FirebaseDatabase.getInstance().getReference("Bus")
        val checkQuery = dbRef
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
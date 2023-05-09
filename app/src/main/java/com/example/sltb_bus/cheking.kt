package com.example.sltb_bus

import BusModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sltb_bus.Adapter.BookingbusSerchAdapter
import com.example.sltb_bus.Adapter.TogoBusAdapter
import com.google.firebase.database.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class cheking : AppCompatActivity() {

    private lateinit var StartLocation: EditText
    private lateinit var EndLocation: EditText
    private lateinit var btnse: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheking)
        val recyclerView: RecyclerView = findViewById(R.id.rcBus)
        StartLocation = findViewById(R.id.tvStartLocation)
        EndLocation = findViewById((R.id.tvEndLocation))
        btnse = findViewById(R.id.btnserch)
        val current = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        val formatted = current.format(formatter)

        val ui = this
        val adapter = BookingbusSerchAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(ui)
        val email = intent.extras!!.getString("Email")

        btnse.setOnClickListener {
            dbRef = FirebaseDatabase.getInstance().getReference("Bus")
            val checkQuery = dbRef.orderByChild("date").equalTo(formatted)
            checkQuery.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val tempList = ArrayList<BusModel>()
                    for (snapshot in dataSnapshot.children) {
                        val bus = snapshot.getValue(BusModel::class.java)
                        if ((bus?.StartLocation.equals(StartLocation.text.toString())) && (bus?.EndLocation.equals(
                                EndLocation.text.toString()
                            ))
                        ) {
                            bus?.let { tempList.add(it) }
                        }
                    }
                    adapter.setData(tempList, ui)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
        }
    }
}


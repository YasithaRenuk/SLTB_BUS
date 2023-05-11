package com.example.sltb_bus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sltb_bus.Adapter.BookBusAdapter
import com.example.sltb_bus.Adapter.TogoBusAdapter
import com.example.sltb_bus.Models.BookModel
import com.google.firebase.database.*

class BookingCheckActivity : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_check)
        val recyclerView: RecyclerView = findViewById(R.id.rcBus)

        val ui = this
        val adapter = BookBusAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(ui)
        val email = intent.extras!!.getString("Email")

        dbRef = FirebaseDatabase.getInstance().getReference("Book")
        val checkQuery = dbRef.orderByChild("pemail").equalTo(email)
        checkQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val tempList = ArrayList<BookModel>()
                for (snapshot in dataSnapshot.children) {
                    val bus = snapshot.getValue(BookModel::class.java)
                    bus?.let { tempList.add(it) }
                }
                adapter.setData(tempList, ui)
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }
}
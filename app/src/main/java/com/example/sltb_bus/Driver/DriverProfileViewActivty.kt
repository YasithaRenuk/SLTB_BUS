package com.example.sltb_bus.Driver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.sltb_bus.R
import com.google.firebase.database.*

class DriverProfileViewActivty : AppCompatActivity() {

    private lateinit var DFname : TextView
    private lateinit var DLname : TextView
    private lateinit var DAge : TextView
    private lateinit var DDID : TextView
    private lateinit var DEmail : TextView
    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_profile_view_activty)

        DFname = findViewById(R.id.tvFristname)
        DLname = findViewById(R.id.tvLastname)
        DAge = findViewById(R.id.tvAge)
        DDID = findViewById(R.id.tVId)
        DEmail = findViewById(R.id.tvEmail)

        val email = intent.extras!!.getString("Email")
        val Type = intent.extras!!.getString("Type")
        if (email != null) {
            if (Type != null) {
                readdata(email,Type)
            }
        }


    }

    private  fun readdata(name:String,Type:String){
        if(Type.equals("Driver")) {
            dbRef = FirebaseDatabase.getInstance().getReference("Drivers")
        }else{
            dbRef = FirebaseDatabase.getInstance().getReference("Passengers")
        }
        val cheak = dbRef.orderByChild("email").equalTo(name)
        cheak.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (ds in dataSnapshot.children) {
                        val email = ds.child("email").getValue(String::class.java)
                        val fristname = ds.child("fristName").getValue(String::class.java)
                        val lastname = ds.child("lastName").getValue(String::class.java)
                        val age = ds.child("age").getValue(String::class.java)
                        val nic = ds.child("nic").getValue(String::class.java)

                        DFname.setText(fristname)
                        DLname.setText(lastname)
                        DAge.setText(age)
                        DDID.setText(nic)
                        DEmail.setText(email)

                    }
                } else {
                    Toast.makeText(this@DriverProfileViewActivty,"Cannot find Data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@DriverProfileViewActivty,"Error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
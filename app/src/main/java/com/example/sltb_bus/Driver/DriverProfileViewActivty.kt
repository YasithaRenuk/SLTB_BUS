package com.example.sltb_bus.Driver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.sltb_bus.DLoginActivity
import com.example.sltb_bus.R
import com.example.sltb_bus.UpdateActivity
import com.example.sltb_bus.UserTypeActivity
import com.google.firebase.database.*

class DriverProfileViewActivty : AppCompatActivity() {

    private lateinit var DFname : TextView
    private lateinit var DLname : TextView
    private lateinit var DAge : TextView
    private lateinit var DDID : TextView
    private lateinit var DEmail : TextView
    private  lateinit var btUpdate :Button
    private  lateinit var btDelete :Button
    private  lateinit var id:String
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_profile_view_activty)

        DFname = findViewById(R.id.tvFristname)
        DLname = findViewById(R.id.tvLastname)
        DAge = findViewById(R.id.tvAge)
        DDID = findViewById(R.id.tVId)
        DEmail = findViewById(R.id.tvEmail)
        btUpdate = findViewById(R.id.update)
        btDelete = findViewById(R.id.Delete)

        val email = intent.extras!!.getString("Email")
        val Type = intent.extras!!.getString("Type")
        if (email != null) {
            if (Type != null) {
                readdata(email,Type)
            }
        }

        btUpdate.setOnClickListener {
            if (Type != null) {
                sendtoupdate(Type)
            }
        }

        btDelete.setOnClickListener {
            if (Type != null) {
                delete(Type)
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
                        if(Type.equals("Driver")) {
                            id = ds.child("DriverID").getValue(String::class.java).toString()
                        }else{
                            id = ds.child("PassengerID").getValue(String::class.java).toString()
                        }
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
    private  fun sendtoupdate(type:String){
        val Fname = DFname.text.toString()
        val Lname = DLname.text.toString()
        val age = DAge.text.toString()
        val nic = DDID.text.toString()
        val email = DEmail.text.toString()

        val intent = Intent(this, UpdateActivity::class.java)
        intent.putExtra("Type",type)
        intent.putExtra("Fname",Fname)
        intent.putExtra("Lname",Lname)
        intent.putExtra("age",age)
        intent.putExtra("nic",nic)
        intent.putExtra("Email",email)
        intent.putExtra("id",id)
        startActivity(intent)
    }

    private  fun delete(Type:String){
        if(Type.equals("Driver")) {
            dbRef = FirebaseDatabase.getInstance().getReference("Drivers")
        }else{
            dbRef = FirebaseDatabase.getInstance().getReference("Passengers")
        }
        dbRef.child(id).removeValue().addOnSuccessListener {
            Toast.makeText(this, "Successfly Deleted", Toast.LENGTH_LONG).show()
            val intent =Intent(this, UserTypeActivity::class.java)
            startActivity(intent)
        }.addOnFailureListener {
            Toast.makeText(this, "Failured Deleted", Toast.LENGTH_LONG).show()
        }
    }
}
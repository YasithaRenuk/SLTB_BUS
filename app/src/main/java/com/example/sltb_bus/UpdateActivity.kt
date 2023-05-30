package com.example.sltb_bus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.sltb_bus.Driver.DriverProfileViewActivty
import com.google.firebase.database.*

class UpdateActivity : AppCompatActivity() {
    private lateinit var DFname : EditText
    private lateinit var DLname : EditText
    private lateinit var DAge : EditText
    private lateinit var DDID : EditText
    private lateinit var DEmail : EditText
    private  lateinit var btUpdate : Button
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        DFname = findViewById(R.id.tvFristname)
        DLname = findViewById(R.id.tvLastname)
        DAge = findViewById(R.id.tvAge)
        DDID = findViewById(R.id.tVId)
        DEmail = findViewById(R.id.tvEmail)
        btUpdate = findViewById(R.id.update)

        val email = intent.extras!!.getString("Email")
        val Type = intent.extras!!.getString("Type")
        val fname = intent.extras!!.getString("Fname")
        val lname = intent.extras!!.getString("Lname")
        val age = intent.extras!!.getString("age")
        val nic = intent.extras!!.getString("nic")
        var id = intent.extras!!.getString("id")

        DFname.setText(fname)
        DLname.setText(lname)
        DAge.setText(age)
        DDID.setText(nic)
        DEmail.setText(email)
//run update function when click the button
        btUpdate.setOnClickListener {
            if (Type != null && id != null) {
                readdata(Type, id);
            }


        }

    }
    private  fun readdata(Type:String,id:String){
        val age = DAge.text.toString()
        val email = DEmail.text.toString()
        val fname = DFname.text.toString()
        val lname = DLname.text.toString()
        val nic = DDID.text.toString()

        if(Type.equals("Driver")) {
            dbRef = FirebaseDatabase.getInstance().getReference("Drivers")
        }else{
            dbRef = FirebaseDatabase.getInstance().getReference("Passengers")
        }
        val User = HashMap<String,Any>()
        User.put("age",age)
        User.put("email",email)
        User.put("fristName",fname)
        User.put("lastName",lname)
        User.put("nic",nic)
//update profile
        val cheak = dbRef.child(id).updateChildren(User).addOnCompleteListener {
           //toast message
            if(it.isSuccessful){
                Toast.makeText(this, "Data Update successfully", Toast.LENGTH_LONG).show()
                val intent = Intent(this,DriverProfileViewActivty::class.java)
                intent.putExtra("Email",email)
                intent.putExtra("Type",Type)
                startActivity(intent)
            }
        } .addOnFailureListener { err ->
            Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
        }
    }

}
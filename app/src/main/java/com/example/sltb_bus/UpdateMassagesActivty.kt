package com.example.sltb_bus

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.sltb_bus.Driver.MainActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateMassagesActivty : AppCompatActivity() {

    private lateinit var bdate: EditText
    private  lateinit var busnumber: EditText
    private lateinit var btupdate : Button

    private lateinit var dbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_massages_activty)

        bdate = findViewById(R.id.etdate)
        btupdate = findViewById(R.id.btnupdate)
        busnumber = findViewById(R.id.etbusNumber)

        val MsqID = intent.extras!!.getString("MsqID")
        val Massage = intent.extras!!.getString("Massage")
        val Date = intent.extras!!.getString("Date")

        bdate.setText(Date)
        busnumber.setText(Massage)

        btupdate.setOnClickListener {
            val Mass = HashMap<String,Any>()
            if (MsqID != null) {
                Mass.put("msqID", MsqID.toString())
                Mass.put("massage", busnumber.text.toString())
                Mass.put("date", bdate.text.toString())
                dbRef = FirebaseDatabase.getInstance().getReference("Massages")
                val cheak = dbRef.child(MsqID).updateChildren(Mass).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(this, "Update Successfully", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, AdminDashbordActivty::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}
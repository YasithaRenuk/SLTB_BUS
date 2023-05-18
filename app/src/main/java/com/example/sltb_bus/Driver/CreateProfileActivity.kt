package com.example.sltb_bus.Driver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.sltb_bus.DLoginActivity
import com.example.sltb_bus.Models.AdminModel
import com.example.sltb_bus.Models.DriverModel
import com.example.sltb_bus.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateProfileActivity : AppCompatActivity() {

    private lateinit var etFName: EditText
    private lateinit var etLName: EditText
    private lateinit var etNIC: EditText
    private lateinit var etAge: EditText
    private lateinit var btsave :Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)

        etFName = findViewById(R.id.etFristName)
        etLName = findViewById(R.id.etLastName)
        etNIC = findViewById(R.id.etNIC)
        etAge = findViewById(R.id.etAge)
        btsave = findViewById(R.id.btnsave)

        dbRef = FirebaseDatabase.getInstance().getReference("Drivers")

        val email = intent.extras!!.getString("Email")

        btsave.setOnClickListener {
            if (email != null) {
                savedriver(email)
            }
        }
    }

    private fun savedriver(email:String){
        val fname = etFName.text.toString()
        val lname = etLName.text.toString()
        val nic = etNIC.text.toString()
        val age = etAge.text.toString()

        if(fname.isNotEmpty() && lname.isNotEmpty() && nic.isNotEmpty() && age.isNotEmpty()){
            val DriverId = dbRef.push().key!!

            val driver = DriverModel(DriverId,email,fname,lname,nic,age)
            dbRef.child(DriverId).setValue(driver)
                .addOnCompleteListener {
                    Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                    etFName.text.clear()
                    etLName.text.clear()
                    etNIC.text.clear()
                    etAge.text.clear()

                    val intent = Intent(this, DLoginActivity::class.java)
                    intent.putExtra("Type","Driver")
                    startActivity(intent)

                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
        }else{
            Toast.makeText(this,"Fields cannot be emty", Toast.LENGTH_SHORT).show()
        }
    }
}
package com.example.sltb_bus.Driver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.sltb_bus.Models.BusModel
import com.example.sltb_bus.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddBusActivity : AppCompatActivity() {

    private lateinit var startl: EditText
    private lateinit var endl: EditText
    private lateinit var startt: EditText
    private lateinit var endt: EditText
    private lateinit var bdate: EditText
    private lateinit var nofoseat: EditText
    private lateinit var btsave : Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_bus)

        startl = findViewById(R.id.etstlocation)
        endl = findViewById(R.id.etendlocation)
        startt = findViewById(R.id.etsttime)
        endt = findViewById(R.id.etendtime)
        bdate = findViewById(R.id.etdate)
        nofoseat = findViewById(R.id.etNoFoSeat)
        btsave = findViewById(R.id.btnsave)

        dbRef = FirebaseDatabase.getInstance().getReference("Bus")

        val email = intent.extras!!.getString("Email")

        btsave.setOnClickListener {
            if (email != null) {
                Addbus(email)
            }
        }

    }

    private fun Addbus(name:String){
        val sl = startl.text.toString()
        val el = endl.text.toString()
        val st = startt.text.toString()
        val et = endt.text.toString()
        val date =bdate.text.toString()
        val nfs = nofoseat.text.toString().toInt()

        if(sl.isNotEmpty() && el.isNotEmpty() && st.isNotEmpty() && et.isNotEmpty()){

            val BusId = dbRef.push().key!!
            val bus = BusModel(BusId,name,sl,el,st,et,date,nfs,0)
            dbRef.child(BusId).setValue(bus)
                .addOnCompleteListener {
                    Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                    startl.text.clear()
                    endl.text.clear()
                    startt.text.clear()
                    endt.text.clear()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
        }else{
            Toast.makeText(this,"Fields cannot be emty", Toast.LENGTH_SHORT).show()
        }
    }
}
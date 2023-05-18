package com.example.sltb_bus

import BusModel
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

class AddbusAdminActivity : AppCompatActivity() {

    private lateinit var startl: EditText
    private lateinit var endl: EditText
    private lateinit var startt: EditText
    private lateinit var endt: EditText
    private lateinit var bdate: EditText
    private lateinit var nofoseat: EditText
    private  lateinit var busnumber: EditText
    private  lateinit var busemail:EditText
    private lateinit var btsave : Button

    private lateinit var dbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addbus_admin)

        startl = findViewById(R.id.etstlocation)
        endl = findViewById(R.id.etendlocation)
        startt = findViewById(R.id.etsttime)
        endt = findViewById(R.id.etendtime)
        bdate = findViewById(R.id.etdate)
        nofoseat = findViewById(R.id.etNoFoSeat)
        btsave = findViewById(R.id.btnsave)
        busemail = findViewById(R.id.etEmail)
        busnumber = findViewById(R.id.etbusNumber)

        dbRef = FirebaseDatabase.getInstance().getReference("Bus")


        btsave.setOnClickListener {
            Addbus()
        }

    }

    private fun Addbus(){
        val sl = startl.text.toString()
        val el = endl.text.toString()
        val st = startt.text.toString()
        val et = endt.text.toString()
        val date =bdate.text.toString()
        val nfs = nofoseat.text.toString().toInt()
        val bnumber = busnumber.text.toString()
        val name = busemail.text.toString()

        if(sl.isNotEmpty() && el.isNotEmpty() && st.isNotEmpty() && et.isNotEmpty() && name.isNotEmpty() && bnumber.isNotEmpty() ){

            val BusId = dbRef.push().key!!
            val bus = BusModel(BusId,bnumber,name,sl,el,st,et,date,nfs,0)
            dbRef.child(BusId).setValue(bus)
                .addOnCompleteListener {
                    Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                    startl.text.clear()
                    endl.text.clear()
                    startt.text.clear()
                    endt.text.clear()

                    val intent = Intent(this, AdminDashbordActivty::class.java)
                    startActivity(intent)

                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
        }else{
            Toast.makeText(this,"Fields cannot be emty", Toast.LENGTH_SHORT).show()
        }
    }
}
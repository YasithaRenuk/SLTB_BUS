package com.example.sltb_bus

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.sltb_bus.Models.MassageModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AddMassagesActivty : AppCompatActivity() {

    private lateinit var etMassages:EditText
    private lateinit var btSave:Button

    private lateinit var dbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_massages_activty)

        etMassages = findViewById(R.id.etMassages)
        btSave = findViewById(R.id.btnsave)
        val current = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        val formatted = current.format(formatter)

        dbRef = FirebaseDatabase.getInstance().getReference("Massages")

        btSave.setOnClickListener {
            Addbus(formatted)
        }

    }
    private fun Addbus(date:String){
        val Massage = etMassages.text.toString()

        if(Massage.isNotEmpty()){

            val MsgId = dbRef.push().key!!
            val Massage = MassageModel(MsgId,Massage,date)
            dbRef.child(MsgId).setValue(Massage)
                .addOnCompleteListener {
                    Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                    etMassages.text.clear()

                    val intent = Intent(this, AdminDashbordActivty::class.java)
                    startActivity(intent)

                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
        }else{
            Toast.makeText(this,"Field cannot be emty", Toast.LENGTH_SHORT).show()
        }
    }
}
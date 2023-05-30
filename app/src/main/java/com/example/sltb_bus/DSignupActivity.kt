package com.example.sltb_bus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sltb_bus.Driver.CreateProfileActivity
import com.example.sltb_bus.Passenger.CreatePassengerActivity
import com.example.sltb_bus.databinding.ActivityDsignupBinding
import com.google.firebase.auth.FirebaseAuth

class DSignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDsignupBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDsignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        val Type = intent.extras!!.getString("Type")

        binding.signupButton.setOnClickListener {
            val email = binding.sigupEmail.text.toString()
            val password = binding.sigupPassword.text.toString()
            val configPassword = binding.sigupConfing.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty() && configPassword.isNotEmpty()){
                if(password == configPassword){
                   firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                       if(it.isSuccessful){
                           if(Type.equals("Driver")) {
                               val intent = Intent(this, CreateProfileActivity::class.java)
                               intent.putExtra("Email", email)
                               startActivity(intent)
                           }else{
                               val intentq = Intent(this, CreatePassengerActivity::class.java)
                               intentq.putExtra("Email", email)
                               startActivity(intentq)
                           }
                       }else{
                           Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                       }
                   }
                }else{
                    Toast.makeText(this,"Password does not matched",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Fields cannot be emty",Toast.LENGTH_SHORT).show()
            }
        }
        binding.LoginRedirectText.setOnClickListener {
            val loginIntent = Intent(this,DLoginActivity::class.java)
            intent.putExtra("Type",Type)
            startActivity(loginIntent)
        }
    }
}
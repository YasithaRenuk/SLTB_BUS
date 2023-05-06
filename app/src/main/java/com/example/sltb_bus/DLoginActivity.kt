package com.example.sltb_bus

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sltb_bus.Driver.MainActivity
import com.example.sltb_bus.databinding.ActivityDloginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class DLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDloginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDloginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        val type = intent.extras?.getString("Type")

        binding.loginButton.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { authTask ->
                    if (authTask.isSuccessful) {
                        val dbRefPath = if (type == "Driver") "Drivers" else "Passengers"
                        dbRef = FirebaseDatabase.getInstance().getReference(dbRefPath)
                        val checkQuery = dbRef.orderByChild("email").equalTo(email)
                        checkQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    val nextActivity = if (type == "Driver") {
                                        Intent(this@DLoginActivity, MainActivity::class.java)
                                    } else {
                                        Intent(this@DLoginActivity, PassengerDashBordActivtiy::class.java)
                                    }
                                    nextActivity.putExtra("Email", email)
                                    startActivity(nextActivity)
                                    finish()
                                } else {
                                    val intent = Intent(this@DLoginActivity, UserTypeActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                Toast.makeText(this@DLoginActivity, "Error", Toast.LENGTH_SHORT).show()
                            }
                        })
                    } else {
                        Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        binding.SignupRedirectText.setOnClickListener {
            val loginintent = Intent(this, DSignupActivity::class.java)
            loginintent.putExtra("Type", type)
            startActivity(loginintent)
            finish()
        }
    }
}

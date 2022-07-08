package com.example.projectapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.projectapp.Recruiter.RecruiterDashboard
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class ActivitySignIn : AppCompatActivity() {

    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val emailLayout = findViewById<TextInputLayout>(R.id.etSignInEmail)
        val passwordLayout = findViewById<TextInputLayout>(R.id.etSignInPassword)
        val signinBtn = findViewById<Button>(R.id.btnSignIn)
        val createAccountBtn = findViewById<Button>(R.id.btnCreateAccount)

        createAccountBtn.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        signinBtn.setOnClickListener {
            val email = emailLayout.editText?.text.toString()
            val password = passwordLayout.editText?.text.toString()

            if (email.isNotBlank() and password.isNotBlank()) {
                login(email, password, signinBtn)
            }else{
                Toast.makeText(this,"Please fill all fields!",Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        if (auth.currentUser != null){
            val intent = Intent(this, RecruiterDashboard::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun login(email:String, password:String,signinBtn: Button){
        signinBtn.isEnabled = false

        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
            signinBtn.isEnabled = true
            val intent = Intent(this, RecruiterDashboard::class.java)
            startActivity(intent)
            finish()
        }.addOnFailureListener {
            signinBtn.isEnabled = true
            Toast.makeText(this,it.message,Toast.LENGTH_LONG).show()
        }.addOnCanceledListener {
            signinBtn.isEnabled = true
            Toast.makeText(this,"Login Cancelled!",Toast.LENGTH_LONG).show()
        }

    }
}
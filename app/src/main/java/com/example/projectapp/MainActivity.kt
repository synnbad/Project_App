package com.example.projectapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.example.projectapp.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    var databaseReference : DatabaseReference? = null
    var database : FirebaseDatabase? = null
    private lateinit var binding: ActivityMainBinding
    lateinit var etEmail: TextInputEditText
    lateinit var etConfPass: TextInputEditText
    private lateinit var etPass: TextInputEditText
    private lateinit var btnSignUp: Button
    private lateinit var firstnameInput: TextInputEditText
    private lateinit var lastnameInput: TextInputEditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // View Bindings
        etEmail = findViewById(R.id.emailAddress)
        etPass = findViewById(R.id.password)
        etConfPass = findViewById(R.id.confirmPassword)
        btnSignUp = findViewById(R.id.createBtn)
        firstnameInput = findViewById(R.id.firstnameInput)
        lastnameInput = findViewById(R.id.lastnameInput)


        // Initialising auth object
        auth = Firebase.auth

        database = FirebaseDatabase.getInstance()

        databaseReference =database?.reference!!.child("profile")

        val items = listOf("Student", "Recruiter")
        val adapter = ArrayAdapter(this, R.layout.account_item, items)

        (binding.accountType.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        (binding.accountType.editText as? AutoCompleteTextView)?.setOnItemClickListener { adapterView, view, position, id ->
            when (position){
                0 -> {
                    binding.studentDetails.visibility = VISIBLE
                    binding.recruiterDetails.visibility = GONE
                }
                1 -> {
                    binding.studentDetails.visibility = GONE
                    binding.recruiterDetails.visibility = VISIBLE
                }
                else -> {
                    binding.studentDetails.visibility = GONE
                    binding.recruiterDetails.visibility = GONE
                }
            }

        }

        binding.createBtn.setOnClickListener {
            signUpUser()
        }

        binding.tvRedirectLogin.setOnClickListener {
            val intent = Intent(this, ActivitySignIn::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signUpUser() {
        val email = etEmail.text.toString()
        val pass = etPass.text.toString()
        val confirmPassword = etConfPass.text.toString()

        // check pass
        if (email.isBlank() || pass.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }

        if (pass != confirmPassword) {
            Toast.makeText(this, "Password and Confirm Password do not match", Toast.LENGTH_SHORT)
                .show()
            return
        }
        // If all credential are correct
        // We call createUserWithEmailAndPassword
        // using auth object and pass the
        // email and pass in it.
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Successfully Signed Up", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Sign Up Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }





    }





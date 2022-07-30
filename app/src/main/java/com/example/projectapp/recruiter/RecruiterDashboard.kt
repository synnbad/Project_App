package com.example.projectapp.recruiter

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.projectapp.ActivitySignIn
import com.example.projectapp.R
import com.example.projectapp.databinding.ActivityDashboardBinding
import com.google.firebase.auth.FirebaseAuth

class RecruiterDashboard : AppCompatActivity() {
    private val auth = FirebaseAuth.getInstance()
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)

        binding.recruiterProfile.setOnClickListener {
            startActivity(Intent(this, Recruiter_profile::class.java))
        }


        binding.addnewadverts.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    AddnewAdverts::class.java
                )
            )
        }





        binding.allapplications.setOnClickListener {
            startActivity(
                Intent(this,
            AllApplications::class.java)
            )
        }


        binding.addnewadverts.setOnClickListener {
            startActivity(
                Intent(this,
                AddnewAdverts::class.java)
            )
        }

        binding.button.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, ActivitySignIn::class.java))
            finish()
        }


    }




}
package com.example.projectapp.recruiter

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.projectapp.R
import com.example.projectapp.databinding.ActivityDashboardBinding
import com.example.projectapp.recruiter_notifications
import com.example.projectapp.schedule

class RecruiterDashboard : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)

        binding.recruiterProfile.setOnClickListener {
            startActivity(Intent(this, Recruiter_profile::class.java))
        }

        binding.recruiterNotification.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    recruiter_notifications::class.java
                )
            )
        }

        binding.addnewadverts.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    AddnewAdverts::class.java
                )
            )
        }

        binding.schedules.setOnClickListener { startActivity(Intent(this, schedule::class.java)) }


    }


}
package com.example.projectapp.students

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.projectapp.ActivitySignIn
import com.example.projectapp.R
import com.example.projectapp.databinding.ActivityStudentDashboardBinding
import com.google.firebase.auth.FirebaseAuth

class StudentDashboard : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()

    private lateinit var binding: ActivityStudentDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_student_dashboard)

        binding.studentProfile.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    StudentProfile::class.java
                )
            )
        }

        binding.button.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this,ActivitySignIn::class.java))
            finish()
        }

        binding.studentInbox.setOnClickListener {
            startActivity(Intent(this, BrowseJobs::class.java))
        }

    }





}









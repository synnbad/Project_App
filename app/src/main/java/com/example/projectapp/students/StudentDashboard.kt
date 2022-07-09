package com.example.projectapp.students

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.projectapp.R
import com.example.projectapp.SignUpActivity
import com.example.projectapp.databinding.ActivityStudentDashboardBinding

class StudentDashboard : AppCompatActivity() {


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

        binding.studentNotifications.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    Notifications::class.java
                )
            )
        }


        binding.studentApplyInternship.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    SignUpActivity::class.java
                )
            )
        }

        binding.studentInbox.setOnClickListener { startActivity(Intent(this, inbox::class.java)) }


    }
}
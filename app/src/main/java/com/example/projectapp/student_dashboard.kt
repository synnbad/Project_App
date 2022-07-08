package com.example.projectapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.projectapp.Students.Notifications
import com.example.projectapp.Students.StudentProfile
import com.example.projectapp.Students.inbox
import com.example.projectapp.databinding.ActivityStudentDashboardBinding

class student_dashboard : AppCompatActivity() {


    private lateinit var binding: ActivityStudentDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_student_profile)

        binding.studentProfile.setOnClickListener { startActivity(Intent(this, StudentProfile::class.java)) }

        binding.studentNotifications.setOnClickListener { startActivity(Intent(this, Notifications::class.java)) }


        binding.studentApplyInternship.setOnClickListener { startActivity(Intent(this,MainActivity::class.java)) }

        binding.studentInbox.setOnClickListener { startActivity(Intent(this,inbox::class.java)) }


}
}
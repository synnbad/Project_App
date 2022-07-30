package com.example.projectapp.recruiter

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.projectapp.R
import com.example.projectapp.databinding.ActivityRecruiterProfileBinding
import com.example.projectapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class Recruiter_profile : AppCompatActivity() {
    private lateinit var binding: ActivityRecruiterProfileBinding

    private val auth = FirebaseAuth.getInstance()
    private val reference = FirebaseDatabase.getInstance().reference.child("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recruiter_profile)

        reference.child(auth.currentUser?.uid!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue<User>()

                binding.recruiterName.text = user?.name
                binding.recruiterField.text = user?.field
                binding.recruiterLocation.text = user?.location
                binding.recruiterCompany.text = user?.company_name
                binding.recruiterEmail.text = user?.email
                binding.recruiterPhone.text = user?.phone
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("STUDENT_PROFILE",error.details)
            }
        })
    }
}
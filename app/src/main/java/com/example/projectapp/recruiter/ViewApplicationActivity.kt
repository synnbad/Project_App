package com.example.projectapp.recruiter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.projectapp.Constants.STUDENT_TYPE
import com.example.projectapp.R
import com.example.projectapp.databinding.ActivityViewApplicationBinding
import com.example.projectapp.model.Advert
import com.example.projectapp.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class ViewApplicationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewApplicationBinding
    val auth = Firebase.auth

    private lateinit var advert: Advert

    private val database = FirebaseDatabase.getInstance()

    private val databaseReference = database.reference.child("adverts")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_view_application)

        val type = intent.getStringExtra("type")
        val adID = intent.getStringExtra("advert_id")

        if (type == STUDENT_TYPE){
            binding.applyBtn.visibility = VISIBLE
        }else{
            binding.applicantsBtn.visibility = VISIBLE
        }

        databaseReference.child(adID!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                advert = snapshot.getValue<Advert>()!!

                advert.apply {
                    binding.CompanyName.text = advert.companyName
                    binding.title.text = advert.jobTitle
                    binding.deadline.text = advert.applyBy
                    binding.description.text = advert.aboutJob
                    binding.salary.text = advert.jobSalary
                    binding.email.text = advert.mail
                    binding.skills.text = advert.skillsRequired
                    binding.additional.text = advert.addationalInfo
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("VIEW_APPLICATION", error.details)
            }
        })

        binding.applyBtn.setOnClickListener {
            applyForJob(advert,adID)
        }

        binding.applicantsBtn.setOnClickListener {
            startActivity(Intent(this,Candidates::class.java).apply {
                putExtra("adID",adID)
            })
        }
    }


    private fun applyForJob(ad: Advert, adID: String?){
        database.reference.child("users").child(auth.currentUser?.uid!!).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue<User>()

                if (user != null){
                    databaseReference.child(adID!!).child("applicants").child(auth.currentUser?.uid!!)
                        .setValue(user).addOnSuccessListener {
                            Toast.makeText(applicationContext,"You have applied!",Toast.LENGTH_LONG).show()
                            finish()
                        }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("APPLY_JOB",error.details)
            }
        })


    }
}
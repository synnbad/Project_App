package com.example.projectapp.recruiter

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projectapp.R
import com.example.projectapp.model.Advert
import com.example.projectapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class AddnewAdverts : AppCompatActivity() {

    private lateinit var advert: Advert
    private lateinit var auth: FirebaseAuth

    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private var companyNameTxt: EditText? = null
    private var jobTitleTxt: EditText? = null
    private var jobDescriptionTxt: EditText? = null
    private var jobSalaryTxt: EditText? = null
    private var applyBy: EditText? = null
    private var requiredSkillsTxt: EditText? = null
    private var additionalInfoTxt: EditText? = null
    var applyJobBtn: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_adverts)

        auth = Firebase.auth

        database = FirebaseDatabase.getInstance()

        databaseReference = database.reference.child("adverts")

        companyNameTxt = findViewById<EditText>(R.id.CompanyNameTxt)
        jobTitleTxt = findViewById<EditText>(R.id.JobTitleEditTxt)
        jobDescriptionTxt = findViewById<EditText>(R.id.AboutJobEditTxt)
        jobSalaryTxt = findViewById<EditText>(R.id.JobSalaryEditTxt)
        applyBy = findViewById<EditText>(R.id.JobLastDateEditTxt)
        requiredSkillsTxt = findViewById<EditText>(R.id.SkillsRequiredEditTxt)
        additionalInfoTxt = findViewById<EditText>(R.id.AddationalInfoEditTxt)

        applyJobBtn = findViewById<View>(R.id.AddJobBtn) as Button?



        applyJobBtn?.setOnClickListener {
            database.reference.child("users").child(auth.currentUser?.uid!!).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue<User>()
                    advert = Advert(
                        "",
                        auth.currentUser?.email,
                        jobDescriptionTxt?.text.toString(),
                        additionalInfoTxt?.text.toString(),
                        auth.currentUser?.uid,
                        user?.company_name,
                        jobSalaryTxt?.text.toString(),
                        jobTitleTxt?.text.toString(),
                        applyBy?.text.toString(),
                        requiredSkillsTxt?.text.toString()
                    )

                    applyForJob(advert)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("ADD_ADVERT",error.details.toString())
                }
            })


        }


    }

    private fun applyForJob(advert: Advert) {

        //key to generate unique value every time we apply for the job
        val key: String? = databaseReference.push().key

        advert.id = key

        //adding the job applicaiton to firebase
        databaseReference.child(key!!).setValue(advert)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireNotNull(applicationContext),"Successfully created advert!",Toast.LENGTH_LONG).show()
                    finish()
                }
            }
    }
}




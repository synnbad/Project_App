package com.example.projectapp.recruiter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.projectapp.Constants
import com.example.projectapp.R
import com.example.projectapp.adapters.CandidatesAdapter
import com.example.projectapp.adapters.CustomAdapter
import com.example.projectapp.databinding.ActivityCandidatesBinding
import com.example.projectapp.model.Advert
import com.example.projectapp.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class Candidates : AppCompatActivity() {
    private var users = ArrayList<User>()
    private lateinit var adapter: CandidatesAdapter
    private lateinit var binding: ActivityCandidatesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_candidates)

        val auth = Firebase.auth
        val adID = intent.getStringExtra("adID")

        val database = FirebaseDatabase.getInstance()

        val databaseReference = database.reference.child("adverts").child(adID!!).child("applicants")

        val recycler = findViewById<View>(R.id.candidates_recycler) as RecyclerView?

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChildren()){
                    for (snapChild in snapshot.children){
                        val user = snapChild.getValue<User>()

                        users.add(user!!)
                    }

                    adapter = CandidatesAdapter(users)
                    recycler?.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("VIEW_CANDIDATES",error.details)
            }
        })
    }
}
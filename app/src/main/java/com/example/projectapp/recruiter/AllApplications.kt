package com.example.projectapp.recruiter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectapp.Constants.RECRUITER_TYPE
import com.example.projectapp.R
import com.example.projectapp.adapters.CustomAdapter
import com.example.projectapp.model.Advert
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class AllApplications : AppCompatActivity() {
    private var adverts = ArrayList<Advert>()
    private lateinit var adapter: CustomAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allapplications)

        val auth = Firebase.auth

        val database = FirebaseDatabase.getInstance()

        val databaseReference = database.reference.child("adverts")

        val recycler = findViewById<View>(R.id.application_recycler) as RecyclerView?

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChildren()){
                    for (snapChild in snapshot.children){
                        val advert = snapChild.getValue<Advert>()

                        adverts.add(advert!!)
                    }

                    adapter = CustomAdapter(adverts, CustomAdapter.OnClickListener {
                        startActivity(
                            Intent(this@AllApplications, ViewApplicationActivity::class.java).apply {
                                putExtra("advert_id",it.id)
                                putExtra("type",RECRUITER_TYPE)
                            }
                        )
                    })
                    recycler?.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


    }
}
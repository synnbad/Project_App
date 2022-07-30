package com.example.projectapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projectapp.Constants.RECRUITER_TYPE
import com.example.projectapp.Constants.STUDENT_TYPE
import com.example.projectapp.model.User
import com.example.projectapp.recruiter.RecruiterDashboard
import com.example.projectapp.students.StudentDashboard
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class ActivitySignIn : AppCompatActivity() {



    private val auth = FirebaseAuth.getInstance()
    private val databaseReference = FirebaseDatabase.getInstance().reference.child("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val emailLayout = findViewById<TextInputLayout>(R.id.etSignInEmail)
        val passwordLayout = findViewById<TextInputLayout>(R.id.etSignInPassword)
        val signinBtn = findViewById<Button>(R.id.btnSignIn)
        val createAccountBtn = findViewById<Button>(R.id.btnCreateAccount)

        createAccountBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        signinBtn.setOnClickListener {
            val email = emailLayout.editText?.text.toString()
            val password = passwordLayout.editText?.text.toString()

            if (email.isNotBlank() and password.isNotBlank()) {
                login(email, password, signinBtn)
            } else {
                Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        if (auth.currentUser != null) {
            databaseReference.child(auth.currentUser?.uid!!).addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue<User>()

                    if (user?.type == STUDENT_TYPE) {
                        val intent = Intent(this@ActivitySignIn, StudentDashboard::class.java)
                        startActivity(intent)
                        finish()
                    } else if (user?.type == RECRUITER_TYPE) {
                        val intent = Intent(this@ActivitySignIn, RecruiterDashboard::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    auth.signOut()
                }
            })
        }
    }

    private fun login(email: String, password: String, signinBtn: Button) {
        signinBtn.isEnabled = false

        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            signinBtn.isEnabled = true

            databaseReference.child(auth.currentUser?.uid!!).addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue<User>()

                    if (user?.type == STUDENT_TYPE) {
                        val intent = Intent(this@ActivitySignIn, StudentDashboard::class.java)
                        startActivity(intent)
                        finish()
                    } else if (user?.type == RECRUITER_TYPE) {
                        val intent = Intent(this@ActivitySignIn, RecruiterDashboard::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    auth.signOut()
                }
            })


        }.addOnFailureListener {
            signinBtn.isEnabled = true
            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
        }.addOnCanceledListener {
            signinBtn.isEnabled = true
            Toast.makeText(this, "Login Cancelled!", Toast.LENGTH_LONG).show()
        }

    }

    fun Logout(view: View) {
        auth.signOut()
    }

}
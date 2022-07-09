package com.example.projectapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.projectapp.Constants.RECRUITER_TYPE
import com.example.projectapp.Constants.STUDENT_TYPE
import com.example.projectapp.databinding.ActivitySignUpBinding
import com.example.projectapp.dto.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private val user = User()

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)


        // Initialising auth object
        auth = Firebase.auth

        database = FirebaseDatabase.getInstance()

        databaseReference = database.reference.child("users")

        val items = listOf("Student", "Recruiter")
        val adapter = ArrayAdapter(this, R.layout.account_item, items)

        val industryItems = listOf(
            "Computer Science / Information Technology",
            "Marketing",
            "Accounting",
            "Banking and Finance",
            "Human Resource"
        )

        val industryAdapter = ArrayAdapter(this, R.layout.account_item, industryItems)

        (binding.accountType.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        (binding.accountType.editText as? AutoCompleteTextView)?.setOnItemClickListener { adapterView, view, position, id ->
            when (position) {
                0 -> {
                    binding.studentDetails.visibility = VISIBLE
                    binding.recruiterDetails.visibility = GONE
                    (binding.fieldOfStudy.editText as? AutoCompleteTextView)?.setAdapter(
                        industryAdapter
                    )
                    user.type = STUDENT_TYPE
                }
                1 -> {
                    binding.studentDetails.visibility = GONE
                    binding.recruiterDetails.visibility = VISIBLE
                    (binding.prefferedFieldOfStudy.editText as? AutoCompleteTextView)?.setAdapter(
                        industryAdapter
                    )
                    user.type = RECRUITER_TYPE
                }
                else -> {
                    binding.studentDetails.visibility = GONE
                    binding.recruiterDetails.visibility = GONE
                }
            }

        }

        binding.createBtn.setOnClickListener {
            signUpUser()
        }

        binding.tvRedirectLogin.setOnClickListener {
            val intent = Intent(this, ActivitySignIn::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signUpUser() {
        val firstname = binding.firstnameInput.editText?.text.toString()
        val lastname = binding.lastnameInput.editText?.text.toString()
        val email = binding.emailAddress.text.toString()
        val phone = binding.phoneLayout.editText?.text.toString()
        val pass = binding.password.text.toString()
        val confirmPassword = binding.confirmPassword.text.toString()

        var university = ""
        var level = ""
        var field = ""

        var companyName = ""
        var location = ""

        if (user.type == STUDENT_TYPE) {
            university = binding.universityLayout.editText?.text.toString()
            level = binding.levelLayout.editText?.text.toString()
            field = binding.fieldOfStudy.editText?.text.toString()
        } else if (user.type == RECRUITER_TYPE) {
            companyName = binding.CompanyName.editText?.text.toString()
            location = binding.Location.editText?.text.toString()
            field = binding.prefferedFieldOfStudy.editText?.text.toString()
        }


        // check fields
        if (firstname.isBlank()) {
            binding.firstnameInput.isErrorEnabled = true
            binding.firstnameInput.error = "First Name Required!"
            return
        }

        if (lastname.isBlank()) {
            binding.lastnameInput.isErrorEnabled = true
            binding.lastnameInput.error = "Last Name Required!"
            return
        }

        if (phone.isBlank()) {
            binding.phoneLayout.isErrorEnabled = true
            binding.phoneLayout.error = "Phone Number Required!"
            return
        }

        if (email.isBlank()) {
            binding.emailLayout.isErrorEnabled = true
            binding.emailLayout.error = "Email Required!"
            return
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailLayout.isErrorEnabled = true
                binding.emailLayout.error = "Invalid Email!"
                return
            }
        }

        if (pass.isBlank()) {
            binding.passwordLayout.isErrorEnabled = true
            binding.passwordLayout.error = "Password Required!"
            return
        }

        if (confirmPassword.isBlank()) {
            binding.etConfirmPassword.isErrorEnabled = true
            binding.etConfirmPassword.error = "Confirm Password Required!"
            return
        }

        if (pass != confirmPassword) {
            binding.etConfirmPassword.isErrorEnabled = true
            binding.etConfirmPassword.error = "Passwords do not match!"
            return
        }

        user.apply {
            this.name = "$firstname $lastname"
            this.email = email
            this.phone = phone
            this.field = field
            this.company_name = companyName
            this.location = location
            this.university = university
            this.level = level
        }


        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task1 ->
            if (task1.isSuccessful) {

                databaseReference.child(auth.currentUser?.uid!!).setValue(user)
                    .addOnCompleteListener { task2 ->
                        if (task2.isSuccessful) {
                            Toast.makeText(this, "Sign Up Complete!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, ActivitySignIn::class.java))
                            finish()
                        } else {
                            auth.currentUser?.delete()?.addOnSuccessListener {
                                Toast.makeText(this, "Sign Up Failed!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

            } else {
                Toast.makeText(this, "Sign Up Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}





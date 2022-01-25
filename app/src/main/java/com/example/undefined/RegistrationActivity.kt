package com.example.undefined

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.undefined.databinding.ActivityRegistrationBinding
import com.example.undefined.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistrationActivity : AppCompatActivity() {
    
    private val auth = FirebaseAuth.getInstance()
    private lateinit var binding: ActivityRegistrationBinding

    private val dbUsers = FirebaseDatabase.getInstance().getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        init()
    }
    
    private fun init() {
        
        binding.buttonRegistration.setOnClickListener { 
            
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            val confirmPassword = binding.editTextConfirmPassword.text.toString()
            
            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "გთხოვთ შეავსოთ ფორმა.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            if (password != confirmPassword) {
                Toast.makeText(this, "განმეორებითი პაროლი არასწორია", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()

                    val key = dbUsers.push().key

                    val user = User(key, auth.currentUser!!.email)

                    dbUsers.child(key.toString()).setValue(user)

                } else {
                    Toast.makeText(this, "რეგისტრაცია ვერ მოხერხდა.", Toast.LENGTH_SHORT).show()
                }
            }
            
        }
        
        binding.buttonLogin.setOnClickListener { 
            finish()
        }
        
    }
    
}
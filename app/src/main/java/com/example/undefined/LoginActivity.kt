package com.example.undefined

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.undefined.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        if (auth.currentUser != null) {
            goToMainActivity()
        }
        setContentView(binding.root)
        init()
    }

    private fun init() {

        binding.buttonLogin.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "გთხოვთ შეავსოთ ფორმა.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    goToMainActivity()
                } else {
                    Toast.makeText(this, "ავტორიზაცია ვერ მოხერხდა.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.buttonRegistration.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
        
    }
    
    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}
package com.example.undefined

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.undefined.databinding.ActivityAddVacancyBinding
import com.example.undefined.model.Vacancy
import com.google.firebase.database.FirebaseDatabase

class AddVacancyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddVacancyBinding
    private val db = FirebaseDatabase.getInstance().getReference("vacancies")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityAddVacancyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.addVacancy.setOnClickListener {
            val title = binding.title.text.toString()
            val description = binding.description.text.toString()
            val imageUrl = binding.imageUrl.text.toString()

            val key = db.push().key
            val currentVacancy = Vacancy(key, title, description, imageUrl)

            db.child(key.toString()).setValue(currentVacancy)

        }
    }

}
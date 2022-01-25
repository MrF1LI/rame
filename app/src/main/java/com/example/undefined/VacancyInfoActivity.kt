package com.example.undefined

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.undefined.databinding.ActivityVacancyInfoBinding

class VacancyInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVacancyInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityVacancyInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {

        binding.title.text = intent.getStringExtra("title").toString()
        binding.description.text = intent.getStringExtra("description").toString()

        Glide.with(this)
            .load(intent.getStringExtra("imageUrl"))
            .into(binding.imageView)

    }

}
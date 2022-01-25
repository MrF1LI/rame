package com.example.undefined.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.undefined.R
import com.example.undefined.VacancyInfoActivity
import com.example.undefined.adapter.SavedVacancyAdapter
import com.example.undefined.databinding.FragmentHomeBinding
import com.example.undefined.databinding.FragmentSavedBinding
import com.example.undefined.model.Vacancy
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class SavedFragment: Fragment(R.layout.fragment_saved), SavedVacancyAdapter.OnItemClickListener {

    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var arrayListSavedVacancies: ArrayList<Vacancy>

    private val db = FirebaseDatabase.getInstance().getReference("vacancies")
    private val dbUsers = FirebaseDatabase.getInstance().getReference("users")
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        loadSavedVacancies()
    }

    private fun init() {
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        arrayListSavedVacancies = arrayListOf()
    }

    private fun loadSavedVacancies() {
        dbUsers.child(auth.currentUser!!.uid).child("savedVacancies").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val list: ArrayList<String> = arrayListOf()

                    for (dataSnapshot in snapshot.children) {
                        val currentItem = dataSnapshot.value.toString()
                        list.add(currentItem)
                    }

                    db.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (dataSnapshot in snapshot.children) {
                                val currentVacancy = dataSnapshot.getValue(Vacancy::class.java)?: return
                                if (list.contains(currentVacancy.id)) {
                                    arrayListSavedVacancies.add(currentVacancy)
                                }
                            }
                            try {
                                recyclerView.adapter = SavedVacancyAdapter(context!!.applicationContext, arrayListSavedVacancies, this@SavedFragment)
                            } catch (e: Exception) {
                                Log.d("Error", "Aranairi araferi")
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(position: Int) {
        val currentVacancy = arrayListSavedVacancies[position]

        val intent = Intent(activity, VacancyInfoActivity::class.java)

        intent.putExtra("title", currentVacancy.title)
        intent.putExtra("description", currentVacancy.description)
        intent.putExtra("imageUrl", currentVacancy.imageUrl)

        startActivity(intent)
    }

}
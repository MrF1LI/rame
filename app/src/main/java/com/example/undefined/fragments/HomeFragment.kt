package com.example.undefined.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.undefined.R
import com.example.undefined.VacancyInfoActivity
import com.example.undefined.adapter.VacancyAdapter
import com.example.undefined.databinding.FragmentHomeBinding
import com.example.undefined.model.Vacancy
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment: Fragment(R.layout.fragment_home), VacancyAdapter.OnItemClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val db = FirebaseDatabase.getInstance().getReference("vacancies")
    private val dbUsers = FirebaseDatabase.getInstance().getReference("users")
    private val auth = FirebaseAuth.getInstance()
    private lateinit var arrayListVacancy: ArrayList<Vacancy>
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        loadVacancies()
    }

    private fun init() {
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        arrayListVacancy = arrayListOf()
    }

    private fun loadVacancies() {
        db.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    arrayListVacancy.clear()
                    for (dataSnapshot in snapshot.children) {
                        val currentVacancy = dataSnapshot.getValue(Vacancy::class.java)?: return
                        arrayListVacancy.add(currentVacancy)
                    }
                    recyclerView.adapter = VacancyAdapter(context!!.applicationContext, arrayListVacancy, this@HomeFragment)
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
        val currentVacancy = arrayListVacancy[position]

        val intent = Intent(activity, VacancyInfoActivity::class.java)

        intent.putExtra("title", currentVacancy.title)
        intent.putExtra("description", currentVacancy.description)
        intent.putExtra("imageUrl", currentVacancy.imageUrl)

        startActivity(intent)

    }

    override fun onSaveClick(position: Int) {
        val currentVacancy = arrayListVacancy[position]
        val key = dbUsers.child(auth.currentUser!!.uid).child("savedVacancies").push().key

        dbUsers.child(auth.currentUser!!.uid).child("savedVacancies").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val list: ArrayList<String> = arrayListOf()
                    for (dataSnapshot in snapshot.children) {
                        val currentId = dataSnapshot.value.toString()
                        list.add(currentId)
                    }

                    if (!list.contains(currentVacancy.id)) {
                        snapshot.ref.child(key.toString()).setValue(currentVacancy.id).addOnSuccessListener {
                            Toast.makeText(activity, "ვაკანსია შენახულია.", Toast.LENGTH_SHORT).show()
                        }
                    }


                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

//        dbUsers.child(auth.currentUser!!.uid).child("savedVacancies").child(key.toString()).setValue(currentVacancy.id).addOnSuccessListener {
//            Toast.makeText(activity, "ვაკანსია შენახულია.", Toast.LENGTH_SHORT).show()
//        }
    }

}
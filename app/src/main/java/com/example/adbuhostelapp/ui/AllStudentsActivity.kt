package com.example.adbuhostelapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adbuhostelapp.R
import com.example.adbuhostelapp.adapter.AllStudentsAdapter
import com.example.adbuhostelapp.model.RoomApplication
import com.google.firebase.firestore.FirebaseFirestore

class AllStudentsActivity : AppCompatActivity() {

    private val studentsList = mutableListOf<RoomApplication>()
    private lateinit var adapter: AllStudentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_students)

        val rv = findViewById<RecyclerView>(R.id.rvAllStudents)
        rv.layoutManager = LinearLayoutManager(this)

        adapter = AllStudentsAdapter(studentsList)
        rv.adapter = adapter

        loadAllStudents()
    }

    private fun loadAllStudents() {
        FirebaseFirestore.getInstance()
            .collection("room_applications")
            .get()
            .addOnSuccessListener { snapshot ->
                studentsList.clear()
                for (doc in snapshot.documents) {
                    val student = doc.toObject(RoomApplication::class.java)
                    if (student != null) {
                        studentsList.add(student)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load students", Toast.LENGTH_SHORT).show()
            }
    }
}

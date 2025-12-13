package com.example.adbuhostelapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adbuhostelapp.R
import com.example.adbuhostelapp.adapter.ApplicationsAdapter
import com.example.adbuhostelapp.model.RoomApplication
import com.google.firebase.firestore.FirebaseFirestore

class ViewApplicantActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ApplicationsAdapter
    private val applicationsList =
        mutableListOf<Pair<String, RoomApplication>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_applicant)

        recyclerView = findViewById(R.id.rvApplications)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = ApplicationsAdapter(applicationsList)
        recyclerView.adapter = adapter

        loadApplications()
    }

    private fun loadApplications() {
        FirebaseFirestore.getInstance()
            .collection("room_applications")
            .get()
            .addOnSuccessListener { snapshot ->
                applicationsList.clear()
                for (doc in snapshot.documents) {
                    val app = doc.toObject(RoomApplication::class.java)
                    if (app != null) {
                        applicationsList.add(Pair(doc.id, app))
                    }
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(
                    this,
                    "Failed to load applications",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}

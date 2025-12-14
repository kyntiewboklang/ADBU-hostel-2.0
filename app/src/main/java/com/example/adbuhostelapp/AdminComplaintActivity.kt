package com.example.adbuhostelapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adbuhostelapp.adapter.ComplaintAdapter
import com.example.adbuhostelapp.model.Complaint
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class AdminComplaintActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val complaints = mutableListOf<Complaint>()
    private lateinit var adapter: ComplaintAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_complaints)

        val rv = findViewById<RecyclerView>(R.id.rvComplaints)
        rv.layoutManager = LinearLayoutManager(this)
        adapter = ComplaintAdapter(complaints)
        rv.adapter = adapter

        loadComplaints()
    }

    private fun loadComplaints() {
        db.collection("complaints")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                complaints.clear()
                for (doc in result) {
                    val complaint = doc.toObject(Complaint::class.java)
                    complaints.add(complaint)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load complaints", Toast.LENGTH_SHORT).show()
            }
    }
}

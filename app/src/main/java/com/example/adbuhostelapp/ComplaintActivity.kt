package com.example.adbuhostelapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.adbuhostelapp.model.Complaint
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ComplaintActivity : AppCompatActivity() {

    private lateinit var etTitle: EditText
    private lateinit var etDesc: EditText
    private lateinit var btnSubmit: Button

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complaint)

        etTitle = findViewById(R.id.etComplaintTitle)
        etDesc = findViewById(R.id.etComplaintDesc)
        btnSubmit = findViewById(R.id.btnSubmitComplaint)

        btnSubmit.setOnClickListener {
            submitComplaint()
        }
    }

    private fun submitComplaint() {
        val title = etTitle.text.toString().trim()
        val desc = etDesc.text.toString().trim()
        val userId = auth.currentUser?.uid ?: "anonymous"

        if (title.isEmpty() || desc.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val complaint = Complaint(
            title = title,
            description = desc,
            userId = userId
        )

        db.collection("complaints")
            .add(complaint)
            .addOnSuccessListener {
                Toast.makeText(this, "Complaint submitted", Toast.LENGTH_SHORT).show()
                etTitle.text.clear()
                etDesc.text.clear()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to submit complaint", Toast.LENGTH_SHORT).show()
            }
    }
}

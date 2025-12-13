package com.example.adbuhostelapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.text.isEmpty
import kotlin.text.trim


class ComplaintActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complaint)

        val etTitle = findViewById<EditText>(R.id.etComplaintTitle)
        val etDesc = findViewById<EditText>(R.id.etComplaintDesc)
        val btnSubmit = findViewById<Button>(R.id.btnSubmitComplaint)

        btnSubmit.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val description = etDesc.text.toString().trim()

            if (title.isEmpty()) {
                etTitle.error = "Enter a title"
                return@setOnClickListener
            }

            if (description.isEmpty()) {
                etDesc.error = "Enter your complaint"
                return@setOnClickListener
            }

            Toast.makeText(this, "Complaint Submitted!", Toast.LENGTH_LONG).show()

            finish()
        }
    }
}

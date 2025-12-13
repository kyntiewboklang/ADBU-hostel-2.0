package com.example.adbuhostelapp.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.adbuhostelapp.R
import com.google.firebase.firestore.FirebaseFirestore

class AllotRoomActivity : AppCompatActivity() {

    private lateinit var etRoomNumber: EditText
    private lateinit var spinnerRoomType: Spinner
    private lateinit var btnSubmit: Button

    private lateinit var docId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_allot_room)


        docId = intent.getStringExtra("DOC_ID") ?: ""

        // Safety check
        if (docId.isEmpty()) {
            Toast.makeText(this, "Invalid application", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        etRoomNumber = findViewById(R.id.et_room_number)
        spinnerRoomType = findViewById(R.id.spinnerRoomType)
        btnSubmit = findViewById(R.id.btnSubmitAllot)

        setupSpinner()

        btnSubmit.setOnClickListener {
            allotRoom()
        }
    }

    private fun setupSpinner() {
        val roomTypes = listOf("Single", "Double", "Triple")
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            roomTypes
        )
        spinnerRoomType.adapter = adapter
    }

    private fun allotRoom() {
        val roomNo = etRoomNumber.text.toString().trim()
        val roomType = spinnerRoomType.selectedItem.toString()

        if (roomNo.isEmpty()) {
            etRoomNumber.error = "Enter room number"
            return
        }

        FirebaseFirestore.getInstance()
            .collection("room_applications")
            .document(docId)
            .update(
                mapOf(
                    "status" to "Allotted",
                    "allottedRoom" to roomNo,
                    "roomType" to roomType
                )
            )
            .addOnSuccessListener {
                Toast.makeText(this, "Room allotted successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to allot room", Toast.LENGTH_SHORT).show()
            }
    }
}

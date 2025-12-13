package com.example.adbuhostelapp.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.adbuhostelapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FieldValue
import android.app.DatePickerDialog
import java.util.Calendar

class RoomApplicationActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_room)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val etFullName = findViewById<EditText>(R.id.et_full_name)
        val etStudentId = findViewById<EditText>(R.id.et_student_id)
        val etEmail = findViewById<EditText>(R.id.et_email)
        val etPhone = findViewById<EditText>(R.id.et_phone)
        val etDob = findViewById<EditText>(R.id.et_dob)
        val etAddress = findViewById<EditText>(R.id.et_address)

        val spinnerGender = findViewById<Spinner>(R.id.spinner_gender)
        val spinnerRoomType = findViewById<Spinner>(R.id.spinner_room_type)

        val btnSubmit = findViewById<Button>(R.id.btn_submit)

        // 🔽 Spinner data
        spinnerGender.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            listOf("Male", "Female", "Other")
        )

        spinnerRoomType.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            listOf("Single", "Double", "Triple")
        )

        etDob.setOnClickListener {
            val calendar = Calendar.getInstance()

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val formattedDate =
                        "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    etDob.setText(formattedDate)
                },
                year, month, day
            )

            datePicker.show()
        }

        btnSubmit.setOnClickListener {

            val uid = auth.currentUser?.uid
            if (uid == null) {
                Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val data = hashMapOf(
                "uid" to uid,
                "fullName" to etFullName.text.toString().trim(),
                "studentId" to etStudentId.text.toString().trim(),
                "email" to etEmail.text.toString().trim(),
                "phone" to etPhone.text.toString().trim(),
                "gender" to spinnerGender.selectedItem.toString(),
                "roomType" to spinnerRoomType.selectedItem.toString(),
                "dob" to etDob.text.toString().trim(),
                "address" to etAddress.text.toString().trim(),
                "status" to "pending",
                "timestamp" to FieldValue.serverTimestamp()
            )

            // 🔴 Basic validation
            if (data.values.any { it.toString().isEmpty() }) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            db.collection("room_applications")
                .add(data)
                .addOnSuccessListener {
                    Toast.makeText(this, "Application submitted", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to submit", Toast.LENGTH_SHORT).show()
                }
        }
    }
}

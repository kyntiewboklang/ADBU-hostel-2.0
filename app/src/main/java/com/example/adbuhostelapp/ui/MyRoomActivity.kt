package com.example.adbuhostelapp.ui

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.adbuhostelapp.R
import com.example.adbuhostelapp.model.RoomApplication
import com.google.firebase.firestore.FirebaseFirestore

class MyRoomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_room)

        val tvStatus = findViewById<TextView>(R.id.tvStatus)
        val tvRoomNo = findViewById<TextView>(R.id.tvRoomNo)
        val tvRoomType = findViewById<TextView>(R.id.tvRoomType)

        val uid = intent.getStringExtra("UID")

        if (uid.isNullOrEmpty()) {
            tvStatus.text = "User not logged in"
            return
        }

        FirebaseFirestore.getInstance()
            .collection("room_applications")
            .whereEqualTo("uid", uid)
            .limit(1)
            .get()
            .addOnSuccessListener { snapshot ->

                if (snapshot.isEmpty) {
                    tvStatus.text = "Room not allotted yet"
                    tvRoomNo.text = ""
                    tvRoomType.text = ""
                    return@addOnSuccessListener
                }

                val app = snapshot.documents[0]
                    .toObject(RoomApplication::class.java)

                if (app?.status == "Allotted") {
                    tvStatus.text = "Status: Allotted"
                    tvRoomNo.text = "Room Number: ${app.allottedRoom}"
                    tvRoomType.text = "Room Type: ${app.roomType}"
                } else {
                    tvStatus.text = "Room not allotted yet"
                    tvRoomNo.text = ""
                    tvRoomType.text = ""
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load room details", Toast.LENGTH_SHORT).show()
            }
    }
}

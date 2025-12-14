package com.example.adbuhostelapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adbuhostelapp.adapter.AnnouncementAdapter
import com.example.adbuhostelapp.model.Announcement
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class AdminAnnouncementActivity : AppCompatActivity() {

    private lateinit var etTitle: EditText
    private lateinit var etMessage: EditText
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_announcement)

        etTitle = findViewById(R.id.et_title)
        etMessage = findViewById(R.id.et_message)
        val btnPost = findViewById<Button>(R.id.btn_post)

        val rv = findViewById<RecyclerView>(R.id.rv_announcements)
        val tvEmpty = findViewById<TextView>(R.id.tv_no_announcements)

        db = FirebaseFirestore.getInstance()

        // 🔹 Post announcement
        btnPost.setOnClickListener { postAnnouncement() }

        // 🔹 Setup RecyclerView
        val adapter = AnnouncementAdapter()
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        // 🔹 Listen for announcements
        db.collection("announcements")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener(this) { snap, _ ->
                if (snap == null) return@addSnapshotListener

                val list = snap.toObjects(Announcement::class.java)

                if (list.isEmpty()) {
                    tvEmpty.visibility = View.VISIBLE
                    rv.visibility = View.GONE
                } else {
                    tvEmpty.visibility = View.GONE
                    rv.visibility = View.VISIBLE
                    adapter.setData(list)
                }
            }
    }

    private fun postAnnouncement() {
        val title = etTitle.text.toString().trim()
        val message = etMessage.text.toString().trim()

        if (title.isEmpty() || message.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            return
        }

        val announcement = hashMapOf(
            "title" to title,
            "message" to message,
            "timestamp" to System.currentTimeMillis()
        )

        db.collection("announcements")
            .add(announcement)
            .addOnSuccessListener {
                Toast.makeText(this, "Announcement posted", Toast.LENGTH_SHORT).show()
                etTitle.setText("")
                etMessage.setText("")
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to post announcement", Toast.LENGTH_SHORT).show()
            }
    }
}

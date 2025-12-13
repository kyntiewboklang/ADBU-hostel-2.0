package com.example.adbuhostelapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.adbuhostelapp.ui.LoginActivity
import com.example.adbuhostelapp.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        // You can store username later in SharedPreferences
        val username = intent.getStringExtra("username") ?: "User"

        tvWelcome.text = "Welcome, $username"

        btnLogout.setOnClickListener {
            // Clear login state in the future when you add SharedPreferences
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
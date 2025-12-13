package com.example.adbuhostelapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.adbuhostelapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private fun navigateByRole(role: String?) {
        when (role) {
            "admin" -> startActivity(Intent(this, AdminActivity::class.java))
            else -> startActivity(Intent(this, UserActivity::class.java))
        }
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvGoRegister = findViewById<TextView>(R.id.tvGoRegister)

        btnLogin.setOnClickListener {
            val email = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val uid = auth.currentUser!!.uid
                        val db = FirebaseFirestore.getInstance()

                        db.collection("users").document(uid)
                            .get()
                            .addOnSuccessListener { document ->

                                if (document.exists()) {
                                    val role = document.getString("role")
                                    navigateByRole(role)
                                } else {
                                    // 🔧 Auto-create missing user record
                                    val newUser = hashMapOf(
                                        "uid" to uid,
                                        "email" to auth.currentUser?.email,
                                        "role" to "student"
                                    )

                                    db.collection("users")
                                        .document(uid)
                                        .set(newUser)
                                        .addOnSuccessListener {
                                            startActivity(
                                                Intent(this, UserActivity::class.java)
                                            )
                                            finish()
                                        }
                                }
                            }

                    } else {
                        Toast.makeText(
                            this,
                            task.exception?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        tvGoRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }
}

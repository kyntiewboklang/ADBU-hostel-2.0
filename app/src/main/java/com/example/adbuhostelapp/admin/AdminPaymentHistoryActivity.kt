package com.example.adbuhostelapp.admin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adbuhostelapp.R
import com.example.adbuhostelapp.adapter.PaymentAdapter
import com.example.adbuhostelapp.model.Payment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class AdminPaymentHistoryActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView
    private val list = mutableListOf<Payment>()
    private lateinit var adapter: PaymentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_payments)

        rv = findViewById(R.id.rvPayments)
        rv.layoutManager = LinearLayoutManager(this)
        adapter = PaymentAdapter(list)
        rv.adapter = adapter

        loadPayments()
    }

    private fun loadPayments() {
        FirebaseFirestore.getInstance()
            .collection("payments")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                list.clear()
                for (doc in result) {
                    val payment = doc.toObject(Payment::class.java)
                    list.add(payment)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load payments", Toast.LENGTH_SHORT).show()
            }
    }
}

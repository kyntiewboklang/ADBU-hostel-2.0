package com.example.adbuhostelapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class PaymentActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etRoom: EditText
    private lateinit var etAmount: EditText
    private lateinit var spinner: Spinner
    private lateinit var btnPay: Button
    private fun clearFields() {
        etName.text.clear()
        etRoom.text.clear()
        etAmount.text.clear()
        spinner.setSelection(0)
    }

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        etName = findViewById(R.id.etName)
        etRoom = findViewById(R.id.etRoom)
        etAmount = findViewById(R.id.etAmount)
        spinner = findViewById(R.id.paymentMethod)
        btnPay = findViewById(R.id.btnPay)

        setupSpinner()

        btnPay.setOnClickListener {
            validateAndPay()
        }
    }

    private fun setupSpinner() {
        val methods = arrayOf("Cash", "Debit Card")
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            methods
        )
        spinner.adapter = adapter
    }

    private fun validateAndPay() {
        val name = etName.text.toString().trim()
        val room = etRoom.text.toString().trim()
        val amount = etAmount.text.toString().trim()
        val method = spinner.selectedItem.toString()

        if (name.isEmpty() || room.isEmpty() || amount.isEmpty()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        when (method) {
            "Cash" -> {
                savePayment("SUCCESS", "CASH_PAYMENT")
                Toast.makeText(this, "Cash payment recorded", Toast.LENGTH_LONG).show()
            }

            "Debit Card" -> {
                simulateDebitPayment()
            }
        }
    }


    private fun simulateDebitPayment() {
        Toast.makeText(this, "Processing debit card payment...", Toast.LENGTH_SHORT).show()

        android.os.Handler(mainLooper).postDelayed({
            savePayment(
                "SUCCESS",
                "DEBIT_${System.currentTimeMillis()}"
            )
            Toast.makeText(this, "Debit card payment successful", Toast.LENGTH_LONG).show()
        }, 2000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 101) {
            val response = data?.getStringExtra("response") ?: ""

            if (response.contains("SUCCESS", true)) {
                savePayment("SUCCESS", response)
                Toast.makeText(this, "Payment Successful", Toast.LENGTH_LONG).show()
            } else {
                savePayment("FAILED", response)
                Toast.makeText(this, "Payment Failed or Cancelled", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun savePayment(status: String, transactionId: String) {
        val payment = hashMapOf(
            "name" to etName.text.toString(),
            "room" to etRoom.text.toString().toInt(),
            "amount" to etAmount.text.toString().toInt(),
            "method" to spinner.selectedItem.toString(),
            "status" to status,
            "transactionId" to transactionId,
            "timestamp" to FieldValue.serverTimestamp(),
            "userId" to auth.currentUser?.uid
        )

        db.collection("payments")
            .add(payment)
            .addOnSuccessListener {
                clearFields()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to save payment", Toast.LENGTH_SHORT).show()
            }
    }

}

package com.example.adbuhostelapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.adbuhostelapp.R

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val etName = findViewById<EditText>(R.id.etName)
        val etRoom = findViewById<EditText>(R.id.etRoom)
        val etAmount = findViewById<EditText>(R.id.etAmount)
        val paymentMethod = findViewById<Spinner>(R.id.paymentMethod)
        val btnPay = findViewById<Button>(R.id.btnPay)

        // Spinner List
        val methods = arrayOf("UPI", "Debit Card", "Net Banking", "Cash")
        paymentMethod.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, methods)

        btnPay.setOnClickListener {
            val name = etName.text.toString()
            val room = etRoom.text.toString()
            val amount = etAmount.text.toString()

            if (name.isEmpty() || room.isEmpty() || amount.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Payment Successful!", Toast.LENGTH_LONG).show()
            }
        }
    }
}

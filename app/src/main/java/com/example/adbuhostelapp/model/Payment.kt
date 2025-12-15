package com.example.adbuhostelapp.model

data class Payment(
    val name: String = "",
    val room: Int = 0,
    val amount: Int = 0,
    val method: String = "",
    val timestamp: com.google.firebase.Timestamp? = null
)
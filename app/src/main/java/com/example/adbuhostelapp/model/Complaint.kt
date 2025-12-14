package com.example.adbuhostelapp.model

data class Complaint(
    val title: String = "",
    val description: String = "",
    val userId: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "Pending"
)
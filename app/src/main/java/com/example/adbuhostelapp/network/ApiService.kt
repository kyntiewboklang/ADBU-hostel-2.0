package com.example.adbuhostelapp.network

import com.example.adbuhostelapp.model.LoginRequest
import com.example.adbuhostelapp.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("api/users/register")
    fun register(@Body user: User): Call<String>

    @POST("api/users/login")
    fun login(@Body request: LoginRequest): Call<String>
}

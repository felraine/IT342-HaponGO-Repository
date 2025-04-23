package com.example.hapongo.network

import com.example.hapongo.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("/api/users/admin/get_all_users")
    suspend fun getAllUsers(): Response<List<User>>

    // Add other API endpoints for other entities or functionalities
}
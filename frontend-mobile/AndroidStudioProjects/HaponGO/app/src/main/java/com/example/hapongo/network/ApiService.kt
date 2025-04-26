package com.example.hapongo.network

import com.example.hapongo.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("users/admin/get_all_users")
    suspend fun getAllUsers(): Response<List<User>>

    // Login user
    @FormUrlEncoded
    @POST("users/login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<User>

    // Register user
    @POST("users/register")
    suspend fun registerUser(
        @Body user: User
    ): Response<User>

    // Get user by email
    @GET("users/{email}")
    suspend fun getUserByEmail(
        @Path("email") email: String
    ): Response<User>

    // Get user by ID (admin only)
    @GET("users/admin/get_user/{id}")
    suspend fun getUserById(
        @Path("id") id: Int
    ): Response<User>

    // Update user
    @PUT("users/update_user/{id}")
    suspend fun updateUser(
        @Path("id") id: Long,
        @Body user: User
    ): Response<User>

    // Delete user
    @DELETE("users/delete_user/{id}")
    suspend fun deleteUser(
        @Path("id") id: Int
    ): Response<String>

    // Add other API endpoints for other entities or functionalities
}
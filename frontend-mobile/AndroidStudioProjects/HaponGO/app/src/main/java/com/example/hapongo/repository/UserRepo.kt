package com.example.hapongo.repository

import com.example.hapongo.model.User
import com.example.hapongo.network.RetrofitInstance
import retrofit2.Response

class UserRepo {
    suspend fun fetchAllUsers(): Response<List<User>> = RetrofitInstance.api.getAllUsers()
}
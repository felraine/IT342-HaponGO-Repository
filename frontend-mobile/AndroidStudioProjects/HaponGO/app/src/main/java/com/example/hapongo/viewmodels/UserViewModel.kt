package com.example.hapongo.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hapongo.model.User
import com.example.hapongo.repository.UserRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val repository = UserRepo()

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadUsers() {
        viewModelScope.launch {
            val response = repository.fetchAllUsers()
            if (response.isSuccessful) {
                _users.value = response.body() ?: emptyList()
            } else {
                _error.value = "Error ${response.code()}: ${response.message()}"
            }
        }
    }
}
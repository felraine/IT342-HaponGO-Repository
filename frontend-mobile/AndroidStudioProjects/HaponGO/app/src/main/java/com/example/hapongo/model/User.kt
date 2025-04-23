package com.example.hapongo.model

data class User(
    val user_id: Long? = null,
    val account_creation_date: String? = null,
    val email: String,
    val is_admin: Int? = 0,
    val name: String,
    val password: String,
    val profile_picture: String? = null,
    val subscription_status: Int? = 0
)
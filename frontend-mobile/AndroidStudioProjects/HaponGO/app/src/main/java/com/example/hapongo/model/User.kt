package com.example.hapongo.model

import java.io.Serializable

data class User(
    val userId: Long? = null,
    val accountCreationDate: String? = null,
    val email: String,
    val is_admin: Int? = 0,
    val name: String,
    val password: String,
    val profile_picture: String? = null,
    val subscription_status: Int? = 0
): Serializable
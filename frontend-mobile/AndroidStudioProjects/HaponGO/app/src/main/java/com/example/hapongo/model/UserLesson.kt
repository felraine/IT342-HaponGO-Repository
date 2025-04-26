package com.example.hapongo.model

data class UserLesson(
    val id: Int? = null,
    val user: User,
    val lesson: Lesson,
    val isActive: Boolean,
    val isCompleted: Boolean
)
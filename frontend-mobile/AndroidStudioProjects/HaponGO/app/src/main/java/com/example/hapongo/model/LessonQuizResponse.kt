package com.example.hapongo.model

data class LessonQuizResponse(
    val questionId: Int,
    val question: String,
    val choice1: String,
    val choice2: String,
    val choice3: String,
    val choice4: String,
    val answer: String
)

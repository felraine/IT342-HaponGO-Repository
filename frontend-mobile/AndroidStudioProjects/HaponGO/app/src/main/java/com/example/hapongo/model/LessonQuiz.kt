package com.example.hapongo.model

data class LessonQuiz(
    val questionId: Int? = null,
    val lesson: Lesson,
    val question: String,
    val choice1: String,
    val choice2: String,
    val choice3: String,
    val choice4: String,
    val answer: String
)
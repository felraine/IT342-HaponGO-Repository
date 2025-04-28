package com.example.hapongo.model

import java.io.Serializable

data class Lesson(
    val lessonId: Long,
    val lessonName: String,
    val lessonOrder: Int
) : Serializable
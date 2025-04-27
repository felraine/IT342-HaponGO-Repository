package com.example.hapongo.model

data class LessonContent(
    val lessonContentId: Long? = null,
    val lesson: Lesson,
    val japaneseWord: String,
    val pronunciation: String,
    val englishWord: String
)
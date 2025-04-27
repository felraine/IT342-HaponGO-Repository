package com.example.hapongo.model

data class Dictionary(
    val id: Long? = null,
    val englishWord: String,
    val japaneseKanji: String,
    val japaneseReading: String
)
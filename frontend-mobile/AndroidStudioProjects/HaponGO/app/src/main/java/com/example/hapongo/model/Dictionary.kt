package com.example.hapongo.model

data class Dictionary(
    val id: Long? = null,
    val english_word: String,
    val japanese_kanji: String,
    val japanese_reading: String
)
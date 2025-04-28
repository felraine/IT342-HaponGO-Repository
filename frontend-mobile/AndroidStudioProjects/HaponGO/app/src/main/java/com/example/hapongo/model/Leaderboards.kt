package com.example.hapongo.model

data class Leaderboards(
    val leaderboardId: Long? = null,
    val user: User,
    val lessonId: Int,
    val points: Int,
    val updatedAt: String
)
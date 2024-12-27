package com.jetbrains.kmpapp.model

data class Movie(
    val id: Long,
    val title: String,
    val year: Int,
    val thumbnailUrl: String,
    val imdbRating: Double,
    val rottenTomatoesScore: Int,
    val genre: String,
)

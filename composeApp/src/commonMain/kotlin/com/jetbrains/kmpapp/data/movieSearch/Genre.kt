package com.jetbrains.kmpapp.data.movieSearch

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: Int,
    val name: String,
)

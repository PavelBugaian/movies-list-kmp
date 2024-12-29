package com.jetbrains.kmpapp.data.movieSearch

import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
    val genres: List<Genre>,
)

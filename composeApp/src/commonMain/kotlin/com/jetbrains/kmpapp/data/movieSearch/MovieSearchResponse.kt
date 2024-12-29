package com.jetbrains.kmpapp.data.movieSearch

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data classes optimized with kotlinx.serialization
 */
@Serializable
data class MovieSearchResponse(
    val results: List<TMDbMovieResult>,
    val page: Int,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int,
)

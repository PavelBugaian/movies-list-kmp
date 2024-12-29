package com.jetbrains.kmpapp.data.movieSearch

import com.jetbrains.kmpapp.model.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TMDbMovieResult(
    val id: Long,
    val title: String,
    @SerialName("release_date")
    val releaseDate: String = "", // Handle empty dates gracefully
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("genre_ids")
    val genreIds: List<Int> = emptyList(),
) {
    fun toDomain(genreMap: Map<Int, String>): Movie =
        Movie(
            id = id,
            title = title,
            year = releaseDate.take(4).toIntOrNull() ?: 0,
            thumbnailUrl = posterPath?.let { "${TMDbConfig.IMAGE_BASE_URL}$it" } ?: "",
            imdbRating = voteAverage,
            rottenTomatoesScore = 0, // Not available from TMDb
            genre = genreIds.mapNotNull { genreMap[it] }.joinToString(", "),
        )
}

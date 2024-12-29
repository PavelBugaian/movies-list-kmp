package com.jetbrains.kmpapp.data.movieSearch

import com.jetbrains.kmpapp.model.Movie
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieSearchApi(
    private val httpClient: HttpClient,
    private val apiKey: String,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    // Cache for genre mapping
    private var genreMap: Map<Int, String> = emptyMap()

    /**
     * Searches movies and returns them as a Flow
     */
    fun searchMovies(query: String): Flow<List<Movie>> =
        flow {
            try {
                // Ensure genre map is loaded
                if (genreMap.isEmpty()) {
                    genreMap = fetchGenres()
                }

                val searchResult = performSearch(query)
                println("search result: $searchResult")
                val movies = searchResult.results.map { it.toDomain(genreMap) }
                emit(movies)
            } catch (e: Exception) {
                // Log error and rethrow as domain-specific exception
                throw MovieRepositoryException("Failed to search movies", e)
            }
        }.flowOn(dispatcher)

    /**
     * Performs the actual search request
     */
    private suspend fun performSearch(query: String): MovieSearchResponse {
        val encodedQuery = query.encodeUrl()
        println("query: $query encoded: $encodedQuery")

        val url =
            buildString {
                append(TMDbConfig.BASE_URL)
                append("/search/movie")
                append("?query=").append(encodedQuery)
                append("&include_adult=false")
                append("&language=en-US")
                append("&page=1")
            }

        return httpClient
            .get(url) {
                header("Authorization", "Bearer $apiKey")
            }.body()
    }

    /**
     * Fetches genre mapping from TMDb
     */
    private suspend fun fetchGenres(): Map<Int, String> {
        val url = "${TMDbConfig.BASE_URL}/genre/movie/list?language=en-US"
        val request =
            httpClient.get(url) {
                header("Authorization", "Bearer $apiKey")
            }
        val response: GenreResponse = request.body()
        return response.genres.associate { it.id to it.name }
    }
}

/**
 * URL encoding extension with improved performance
 */
private fun String.encodeUrl(): String =
    buildString {
        this@encodeUrl.forEach { char ->
            when {
                char.isLetterOrDigit() -> append(char)
                char == ' ' -> append("+")
                char in "-._~" -> append(char)
                else -> {
                    // Convert char code to 2-digit hex without using String.format
                    val hex =
                        char.code
                            .toString(16)
                            .uppercase()
                            .padStart(2, '0')
                    append("%$hex")
                }
            }
        }
    }

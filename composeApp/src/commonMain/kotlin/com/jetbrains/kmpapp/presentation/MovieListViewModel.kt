package com.jetbrains.kmpapp.presentation
import androidx.lifecycle.ViewModel
import com.jetbrains.kmpapp.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MovieListViewModel : ViewModel() {
    private val sampleMovies =
        listOf(
            Movie(
                id = 1,
                title = "The Shawshank Redemption",
                year = 1994,
                thumbnailUrl = "https://upload.wikimedia.org/wikipedia/en/8/81/ShawshankRedemptionMoviePoster.jpg",
                imdbRating = 9.3,
                rottenTomatoesScore = 91,
                genre = "Drama",
            ),
            Movie(
                id = 2,
                title = "The Godfather",
                year = 1972,
                thumbnailUrl = "https://upload.wikimedia.org/wikipedia/en/1/1c/Godfather_ver1.jpg",
                imdbRating = 9.2,
                rottenTomatoesScore = 98,
                genre = "Crime, Drama",
            ),
            Movie(
                id = 3,
                title = "The Dark Knight",
                year = 2008,
                thumbnailUrl = "https://upload.wikimedia.org/wikipedia/en/1/1c/The_Dark_Knight_%282008_film%29.jpg",
                imdbRating = 9.0,
                rottenTomatoesScore = 94,
                genre = "Action, Crime, Drama",
            ),
        )

    private val _movies = MutableStateFlow(sampleMovies)
    val movies: StateFlow<List<Movie>> = _movies.asStateFlow()
}

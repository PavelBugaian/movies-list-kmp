package com.jetbrains.kmpapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetbrains.kmpapp.data.movieSearch.MovieSearchApi
import com.jetbrains.kmpapp.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieSearchViewModel(
    val movieSearchApi: MovieSearchApi,
) : ViewModel() {
    private val _movies = MutableStateFlow(emptyList<Movie>())
    val movies: StateFlow<List<Movie>> = _movies.asStateFlow()

    fun searchMovies(it: String) {
        viewModelScope.launch {
            movieSearchApi
                .searchMovies(it)
                .collect { searchResults ->
                    _movies.emit(searchResults)
                }
        }
    }

    fun onSelected(movie: Movie) {
        println("on movie selected: $movie")
    }
}

package com.jetbrains.kmpapp.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jetbrains.kmpapp.presentation.MovieSearchViewModel
import com.jetbrains.kmpapp.screens.MovieListItem
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieSearchScreen(onBackClicked: () -> Unit) {
    val viewModel = koinViewModel<MovieSearchViewModel>()
    val movies by viewModel.movies.collectAsState()

    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Search") },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go back",
                        )
                    }
                },
            )
        },
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    // Searching on every second character
                    if (it.length % 2 == 0) {
                        viewModel.searchMovies(it)
                    }
                },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                placeholder = { Text("Search for movies...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                    )
                },
                singleLine = true,
            )

            LazyColumn(
                modifier =
                    Modifier.padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp,
                    ),
            ) {
                items(movies) { movie ->
                    MovieListItem(
                        movie,
                        onClick = {
                            viewModel.onSelected(movie)
                            // Going back
                            onBackClicked()
                        },
                    )

                    // Add divider after each item except the last one
                    if (movie != movies.last()) {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = MaterialTheme.colorScheme.outlineVariant,
                            thickness = 1.dp,
                        )
                    }
                }
            }
        }
    }
}

@file:OptIn(ExperimentalMaterial3Api::class)

package com.jetbrains.kmpapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.jetbrains.kmpapp.model.Movie
import com.jetbrains.kmpapp.presentation.MovieListViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen() {
    val viewModel = koinViewModel<MovieListViewModel>()
    val movies by viewModel.movies.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favorite Movies") },
            )
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
        ) {
            items(movies) { movie ->
                MovieListItem(movie)

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

@Composable
private fun MovieListItem(movie: Movie) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(120.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Thumbnail
        AsyncImage(
            model = movie.thumbnailUrl,
            contentDescription = "${movie.title} poster",
            modifier =
                Modifier
                    .width(80.dp)
                    .fillMaxHeight(),
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Movie details
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = "${movie.year} • ${movie.genre}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Ratings
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                // IMDb Rating
                RatingBadge(
                    label = "IMDb",
                    value = movie.imdbRating.toString(),
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                )

                // Rotten Tomatoes Score
                RatingBadge(
                    label = "RT",
                    value = "${movie.rottenTomatoesScore}%",
                    backgroundColor = MaterialTheme.colorScheme.errorContainer,
                )
            }
        }
    }
}

@Composable
private fun RatingBadge(
    label: String,
    value: String,
    backgroundColor: Color,
) {
    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(4.dp),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}

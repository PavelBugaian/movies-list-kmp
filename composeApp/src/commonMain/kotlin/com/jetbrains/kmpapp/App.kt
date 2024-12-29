package com.jetbrains.kmpapp

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jetbrains.kmpapp.screens.MovieListScreen
import com.jetbrains.kmpapp.screens.search.MovieSearchScreen
import kotlinx.serialization.Serializable

@Serializable
object MovieList

@Serializable
object MovieSearchDestination

@Composable
fun App() {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme(),
    ) {
        Surface {
            val navController: NavHostController = rememberNavController()
            NavHost(navController = navController, startDestination = MovieList) {
                composable<MovieList> {
                    MovieListScreen(navigateToSearch = {
                        navController.navigate(MovieSearchDestination)
                    })
                }
                composable<MovieSearchDestination> {
                    MovieSearchScreen(onBackClicked = {
                        navController.navigateUp()
                    })
                }
            }
        }
    }
}

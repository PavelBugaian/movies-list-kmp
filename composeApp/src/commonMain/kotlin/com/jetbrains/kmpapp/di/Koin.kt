package com.jetbrains.kmpapp.di

import com.jetbrains.kmpapp.constants.Constants
import com.jetbrains.kmpapp.data.InMemoryMuseumStorage
import com.jetbrains.kmpapp.data.KtorMuseumApi
import com.jetbrains.kmpapp.data.MuseumApi
import com.jetbrains.kmpapp.data.MuseumRepository
import com.jetbrains.kmpapp.data.MuseumStorage
import com.jetbrains.kmpapp.data.movieSearch.MovieSearchApi
import com.jetbrains.kmpapp.presentation.MovieListViewModel
import com.jetbrains.kmpapp.presentation.MovieSearchViewModel
import com.jetbrains.kmpapp.screens.detail.DetailViewModel
import com.jetbrains.kmpapp.screens.list.ListViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dataModule =
    module {
        single {
            val json = Json { ignoreUnknownKeys = true }
            HttpClient {
                install(ContentNegotiation) {
                    // TODO Fix API so it serves application/json
                    json(json, contentType = ContentType.Any)
                }
            }
        }

        single<MuseumApi> { KtorMuseumApi(get()) }
        single<MuseumStorage> { InMemoryMuseumStorage() }
        single {
            MuseumRepository(get(), get()).apply {
                initialize()
            }
        }
        single {
            MovieSearchApi(
                httpClient =
                    HttpClient {
                        install(ContentNegotiation) {
                            json(
                                Json {
                                    prettyPrint = true
                                    isLenient = true
                                    ignoreUnknownKeys = true
                                },
                            )
                        }
                    },
                apiKey = Constants.TMDB_API_TOKEN,
                dispatcher = Dispatchers.IO,
            )
        }
    }

val viewModelModule =
    module {
        factoryOf(::ListViewModel)
        factoryOf(::DetailViewModel)
        factoryOf(::MovieListViewModel)
        factoryOf(::MovieSearchViewModel)
    }

fun initKoin() {
    startKoin {
        modules(
            dataModule,
            viewModelModule,
        )
    }
}

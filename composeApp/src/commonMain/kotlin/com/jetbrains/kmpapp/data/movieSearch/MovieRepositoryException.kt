package com.jetbrains.kmpapp.data.movieSearch

/**
 * Custom exception for repository errors
 */
class MovieRepositoryException(
    message: String,
    cause: Throwable? = null,
) : Exception(message, cause)

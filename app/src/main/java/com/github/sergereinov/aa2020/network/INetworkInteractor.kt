package com.github.sergereinov.aa2020.network

interface INetworkInteractor {
    suspend fun loadGenres(): NetworkGenres
    suspend fun loadPopularMovies(): NetworkMovies
    suspend fun loadTopRatedMovies(): NetworkMovies
    suspend fun loadMovieDetails(movieId: Int): NetworkMovieDetails
    suspend fun loadMovieCredits(movieId: Int): NetworkMovieCredits
}

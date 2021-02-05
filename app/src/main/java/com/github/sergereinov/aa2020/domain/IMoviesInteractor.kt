package com.github.sergereinov.aa2020.domain

interface IMoviesInteractor {
    suspend fun getCachedMovies(): List<Movie>
    suspend fun getCachedDetails(movieId: Int): MovieDetails
    suspend fun loadMovies(): List<Movie>
    suspend fun loadMovieDetails(movieId: Int): MovieDetails
}
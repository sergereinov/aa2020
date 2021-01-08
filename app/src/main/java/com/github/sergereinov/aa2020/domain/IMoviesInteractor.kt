package com.github.sergereinov.aa2020.domain

interface IMoviesInteractor {
    suspend fun loadMovies(): List<Movie>
    suspend fun loadMovieDetails(movieId: Int): MovieDetails
}
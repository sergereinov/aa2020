package com.github.sergereinov.aa2020.domain

import kotlinx.coroutines.flow.Flow

interface IMoviesInteractor {
    fun moviesFlow(): Flow<List<Movie>>
    fun detailsFlow(movieId: Int): Flow<MovieDetails>
    suspend fun refreshMovies()
    suspend fun refreshMovieDetails(movieId: Int)
}
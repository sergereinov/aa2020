package com.github.sergereinov.aa2020.domain

import com.github.sergereinov.aa2020.data.Movie

interface IMoviesInteractor {
    suspend fun refresh()
    fun getMovies(): List<Movie>?
    fun getMovie(movieId: Int?): Movie?
}
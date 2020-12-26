package com.github.sergereinov.aa2020.domain

import com.github.sergereinov.aa2020.data.Movie

interface IMoviesLocalDataSource {
    fun setMovies(movies: List<Movie>)
    fun getMovies(): List<Movie>?
    fun getMovie(movieId: Int?): Movie?
}
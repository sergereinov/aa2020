package com.github.sergereinov.aa2020.domain

import com.github.sergereinov.aa2020.data.Movie

class MovieDataSource {
    companion object {
        private var movies: List<Movie>? = null

        fun setMovies(movies: List<Movie>) {
            MovieDataSource.movies = movies
        }

        fun getMovies(): List<Movie> = movies ?: listOf()
        fun getMovie(movieId: Int?): Movie? = movies?.firstOrNull { it.id == movieId }
    }
}
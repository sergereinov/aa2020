package com.github.sergereinov.aa2020.domain

import com.github.sergereinov.aa2020.data.Movie

class MoviesLocalDataSource : IMoviesLocalDataSource {
    private var movies: List<Movie>? = null

    override fun setMovies(movies: List<Movie>) {
        this.movies = movies
    }

    override fun getMovies(): List<Movie>? = movies
    override fun getMovie(movieId: Int?): Movie? = movies?.firstOrNull { it.id == movieId }
}

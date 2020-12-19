package com.github.sergereinov.aa2020.domain

import android.content.Context
import com.github.sergereinov.aa2020.data.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieDataSource {
    companion object {
        private var movies: List<Movie>? = null

        suspend fun loadMovies(context: Context) = withContext(Dispatchers.Main) {
            if (movies == null) {
                movies = com.github.sergereinov.aa2020.data.loadMovies(context)
            }
        }

        fun getMovies(): List<Movie> = movies ?: listOf()
        fun getMovie(movieId: Int?): Movie? = movies?.firstOrNull { it.id == movieId }
    }
}
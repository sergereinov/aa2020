package com.github.sergereinov.aa2020.domain

import android.content.Context
import com.github.sergereinov.aa2020.data.Movie
import com.github.sergereinov.aa2020.data.loadMovies

class MoviesLoader(private val context: Context) : IMoviesLoader {
    override suspend fun load(): List<Movie> = loadMovies(context)
}

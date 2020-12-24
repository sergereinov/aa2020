package com.github.sergereinov.aa2020.domain

import com.github.sergereinov.aa2020.data.Movie

interface IMoviesLoader {
    suspend fun load(): List<Movie>
}

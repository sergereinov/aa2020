package com.github.sergereinov.aa2020.domain

import com.github.sergereinov.aa2020.data.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesInteractor(
    private val loader: IMoviesLoader,
    private val dataSource: IMoviesLocalDataSource
) : IMoviesInteractor {

    override suspend fun refresh() {
        val movies = withContext(Dispatchers.IO) { loader.load() }
        withContext(Dispatchers.Main) { dataSource.setMovies(movies) }
    }

    override fun getMovies(): List<Movie>? = dataSource.getMovies()
    override fun getMovie(movieId: Int?): Movie? = dataSource.getMovie(movieId)
}
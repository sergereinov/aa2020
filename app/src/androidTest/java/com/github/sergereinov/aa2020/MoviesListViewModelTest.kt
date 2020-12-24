package com.github.sergereinov.aa2020

import com.github.sergereinov.aa2020.data.Movie
import com.github.sergereinov.aa2020.domain.IMoviesInteractor
import kotlinx.coroutines.*
import org.junit.Assert.*
import org.junit.Test

class MoviesListViewModelTest {
    @Test
    fun testMoviesInteractorCalls() {

        var refreshCounter: Int = 0
        var getMoviesCounter: Int = 0
        var getMovieCounter: Int = 0

        val mi = object : IMoviesInteractor {
            override suspend fun refresh() {
                withContext(Dispatchers.Main) { refreshCounter++ }
            }

            override fun getMovies(): List<Movie>? {
                getMoviesCounter++
                return listOf()
            }

            override fun getMovie(movieId: Int?): Movie? {
                getMovieCounter++
                return null
            }
        }

        val vm = MoviesListViewModel(mi)

        //wait LiveData
        val allMovies = runBlocking(Dispatchers.Main) { vm.movies.getOrAwaitValue() }

        //check movies
        assertEquals("allMovies", listOf<Movie>(), allMovies)

        //check counters
        assertEquals("refreshCounter", 1, refreshCounter)
        assertEquals("getMoviesCounter", 1, getMoviesCounter)
        assertEquals("getMovieCounter", 0, getMovieCounter)
    }
}
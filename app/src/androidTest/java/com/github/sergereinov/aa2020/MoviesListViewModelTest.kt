package com.github.sergereinov.aa2020

import com.github.sergereinov.aa2020.domain.*
import kotlinx.coroutines.*
import org.junit.Assert.*
import org.junit.Test

class MoviesListViewModelTest {

    @Test
    fun testMoviesInteractorCalls() {

        var loadMoviesCounter: Int = 0
        var loadMovieDetails: Int = 0

        val mi = object : IMoviesInteractor {
            override suspend fun loadMovies(): List<Movie> {
                return withContext(Dispatchers.Main) {
                    loadMoviesCounter++
                    listOf()
                }
            }
            override suspend fun loadMovieDetails(movieId: Int): MovieDetails {
                return withContext(Dispatchers.Main) {
                    loadMovieDetails++
                    MovieDetails(
                        id = 0,
                        title = "",
                        overview = null,
                        minimumAge = 0,
                        genres = listOf(),
                        backdrop = null,
                        voteAverage = 0.0,
                        voteCount = 0,
                        actors = listOf()
                    )
                }
            }
        }

        val vm = MoviesListViewModel(mi)

        //wait LiveData
        val movies = runBlocking(Dispatchers.Main) { vm.movies.getOrAwaitValue() }

        //check movies
        assertEquals("movies", listOf<Movie>(), movies)

        //check counters
        assertEquals("loadMoviesCounter", 1, loadMoviesCounter)
        assertEquals("loadMovieDetails", 0, loadMovieDetails)
    }
}
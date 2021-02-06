package com.github.sergereinov.aa2020

import com.github.sergereinov.aa2020.domain.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.junit.Assert.*
import org.junit.Test

class MoviesListViewModelTest {

    @Test
    fun testMoviesInteractorCalls() {

        var refreshMoviesCounter: Int = 0
        var refreshMovieDetailsCounter: Int = 0

        val mi = object : IMoviesInteractor {
            override fun moviesFlow(): Flow<List<Movie>> {
                return flow {
                    emit(listOf())
                }
            }

            override fun detailsFlow(movieId: Int): Flow<MovieDetails> {
                return flow {}
            }

            override suspend fun refreshMovies() {
                withContext(Dispatchers.Main) {
                    refreshMoviesCounter++
                }
            }

            override suspend fun refreshMovieDetails(movieId: Int) {
                withContext(Dispatchers.Main) {
                    refreshMovieDetailsCounter++
                }
            }
        }

        val vm = MoviesListViewModel(mi)

        //wait LiveData
        val movies = runBlocking(Dispatchers.Main) { vm.movies.getOrAwaitValue() }

        //check movies
        assertEquals("movies", listOf<Movie>(), movies)

        //check counters
        assertEquals("refreshMoviesCounter", 1, refreshMoviesCounter)
        assertEquals("refreshMovieDetailsCounter", 0, refreshMovieDetailsCounter)
    }
}

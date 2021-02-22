package com.github.sergereinov.aa2020

import com.github.sergereinov.aa2020.domain.IMoviesInteractor
import com.github.sergereinov.aa2020.domain.Movie
import com.github.sergereinov.aa2020.domain.MovieDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Assert.*
import org.junit.Test

class MovieDetailsViewModelTest {

    @Test
    fun testMoviesInteractorCalls() {

        var refreshMoviesCounter: Int = 0
        var refreshMovieDetailsCounter: Int = 0

        val movie1 = MovieDetails(
            id = 1,
            title = "title",
            overview = "overview",
            minimumAge = 0,
            genres = listOf(),
            backdrop = "backdrop",
            voteAverage = 0.0,
            voteCount = 0,
            actors = listOf()
        )

        val mi = object : IMoviesInteractor {
            override fun moviesFlow(): Flow<List<Movie>> {
                return flow {}
            }

            override fun detailsFlow(movieId: Int): Flow<MovieDetails> {
                return flow {
                    emit(movie1)
                }
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

        val vm = MovieDetailsViewModel(0, mi)

        //wait LiveData
        val selectedMovie = runBlocking(Dispatchers.Main) { vm.dataMovie.getOrAwaitValue() }

        //check movie
        assertEquals("selectedMovie", movie1, selectedMovie)

        //check counters
        assertEquals("refreshMoviesCounter", 0, refreshMoviesCounter)
        assertEquals("refreshMovieDetailsCounter", 1, refreshMovieDetailsCounter)
    }
}

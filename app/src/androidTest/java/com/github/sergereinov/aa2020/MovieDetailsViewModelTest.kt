package com.github.sergereinov.aa2020

import com.github.sergereinov.aa2020.domain.IMoviesInteractor
import com.github.sergereinov.aa2020.domain.Movie
import com.github.sergereinov.aa2020.domain.MovieDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Assert.*
import org.junit.Test

class MovieDetailsViewModelTest {

    @Test
    fun testMoviesInteractorCalls() {

        var loadMoviesCounter: Int = 0
        var loadMovieDetails: Int = 0

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
            override suspend fun loadMovies(): List<Movie> {
                return withContext(Dispatchers.Main) {
                    loadMoviesCounter++
                    listOf()
                }
            }
            override suspend fun loadMovieDetails(movieId: Int): MovieDetails {
                return withContext(Dispatchers.Main) {
                    loadMovieDetails++
                    movie1
                }
            }
        }

        val vm = MovieDetailsViewModel(0, mi)

        //wait LiveData
        val selectedMovie = runBlocking(Dispatchers.Main) { vm.dataMovie.getOrAwaitValue() }

        //check movie
        assertEquals("selectedMovie", movie1, selectedMovie)

        //check counters
        assertEquals("loadMoviesCounter", 0, loadMoviesCounter)
        assertEquals("loadMovieDetails", 1, loadMovieDetails)
    }
}
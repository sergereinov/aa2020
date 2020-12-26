package com.github.sergereinov.aa2020

import com.github.sergereinov.aa2020.data.Movie
import com.github.sergereinov.aa2020.domain.IMoviesInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Assert.*
import org.junit.Test

class MovieDetailsViewModelTest {

    @Test
    fun testMoviesInteractorCalls() {

        var refreshCounter = 0
        var getMoviesCounter = 0
        var getMovieCounter = 0

        val movie1 = Movie(
            id = 1,
            title = "title",
            overview = "overview",
            poster = "poster",
            backdrop = "backdrop",
            ratings = 0.0f,
            numberOfRatings = 0,
            minimumAge = 0,
            runtime = 0,
            genres = listOf(),
            actors = listOf()
        )

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
                return if (refreshCounter == 0) null else movie1.copy()
            }
        }

        val vm = MovieDetailsViewModel(0, mi)

        //wait LiveData
        val selectedMovie = runBlocking(Dispatchers.Main) { vm.movie.getOrAwaitValue() }

        //check movie
        assertEquals("selectedMovie", movie1, selectedMovie)

        //check counters
        assertEquals("refreshCounter", 1, refreshCounter)
        assertEquals("getMoviesCounter", 0, getMoviesCounter)
        assertEquals("getMovieCounter", 2, getMovieCounter)
    }
}
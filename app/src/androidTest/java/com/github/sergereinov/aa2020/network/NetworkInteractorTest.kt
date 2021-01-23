package com.github.sergereinov.aa2020.network

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NetworkInteractorTest {
    private lateinit var targetAppContext: Context
    private lateinit var networkModule: INetworkInteractor
    private val testMovieId: Int = 464052

    @Before
    fun setup() {
        targetAppContext = InstrumentationRegistry.getInstrumentation().targetContext
        networkModule = NetworkModule(targetAppContext)
    }

    @Test
    fun testLoadGenres() {
        val g = runBlocking(Dispatchers.Main) { networkModule.loadGenres()  }
        assertTrue("loadGenres returns not empty list", g.genres.isNotEmpty() )
    }

    @Test
    fun testLoadPopular() {
        val m = runBlocking(Dispatchers.Main) { networkModule.loadPopularMovies()  }
        assertTrue("loadPopularMovies returns not empty list", m.results.isNotEmpty() )
    }

    @Test
    fun testLoadDetails() {
        val d = runBlocking(Dispatchers.Main) { networkModule.loadMovieDetails(testMovieId) }
        assertTrue("loadMovieDetails returns not empty title", d.title.isNotEmpty() )
    }

    @Test
    fun testLoadCredits() {
        val c = runBlocking(Dispatchers.Main) { networkModule.loadMovieCredits(testMovieId) }
        assertTrue("loadMovieCredits returns not empty 'cast' list", c.cast.isNotEmpty() )
    }
}
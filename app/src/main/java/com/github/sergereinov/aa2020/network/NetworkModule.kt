package com.github.sergereinov.aa2020.network

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path

class NetworkModule(context: Context) : INetworkInteractor {

    override suspend fun loadGenres() = withContext(Dispatchers.IO) {
        retrofitModule.moviesApi.getGenres()
    }

    override suspend fun loadPopularMovies() = withContext(Dispatchers.IO) {
        retrofitModule.moviesApi.getPopular()
    }

    override suspend fun loadMovieDetails(movieId: Int) = withContext(Dispatchers.IO) {
        retrofitModule.moviesApi.getDetails(movieId)
    }

    override suspend fun loadMovieCredits(movieId: Int) = withContext(Dispatchers.IO) {
        retrofitModule.moviesApi.getCredits(movieId)
    }

    private interface MoviesApi {
        @GET("genre/movie/list?api_key=${Server.apiKey}")
        suspend fun getGenres(): NetworkGenres

        @GET("movie/popular?api_key=${Server.apiKey}")
        suspend fun getPopular(): NetworkMovies

        @GET("movie/{movie_id}?api_key=${Server.apiKey}")
        suspend fun getDetails(@Path("movie_id") movieId: Int): NetworkMovieDetails

        @GET("movie/{movie_id}/credits?api_key=${Server.apiKey}")
        suspend fun getCredits(@Path("movie_id") movieId: Int): NetworkMovieCredits
    }

    private class RetrofitModule(context: Context) {
        private val cacheSize = (1 * 1024 * 1024).toLong()
        private val jsonCache = Cache(context.cacheDir, cacheSize)
        private val client = OkHttpClient().newBuilder()
            .cache(jsonCache)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
            .build()

        private val json = Json {
            ignoreUnknownKeys = true
        }

        @Suppress("EXPERIMENTAL_API_USAGE")
        private val retrofit: Retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(Server.apiUrl)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        val moviesApi: MoviesApi = retrofit.create()
    }

    private val retrofitModule = RetrofitModule(context)
}
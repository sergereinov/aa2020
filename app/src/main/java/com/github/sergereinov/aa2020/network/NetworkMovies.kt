package com.github.sergereinov.aa2020.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*
    Queries:
        GET /movie/popular
        GET /movie/top_rated
    Schemas:
        https://developers.themoviedb.org/3/movies/get-popular-movies
        https://developers.themoviedb.org/3/movies/get-top-rated-movies
    data class NetworkMovies
 */

@Serializable
data class NetworkMovies(
    @SerialName("results")
    val results: List<NetworkMovieItem>,
)

@Serializable
data class NetworkMovieItem(
    @SerialName("id")
    val id: Int,
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("title")
    val title: String,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int,
)
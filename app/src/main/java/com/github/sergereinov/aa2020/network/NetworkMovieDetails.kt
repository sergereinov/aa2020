package com.github.sergereinov.aa2020.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*
    Query: GET /movie/{movie_id}
    Schema: https://developers.themoviedb.org/3/movies/get-movie-details
    data class NetworkMovieDetails
 */

@Serializable
data class NetworkMovieDetails(
    @SerialName("id")
    val id: Int,
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("title")
    val title: String,
    @SerialName("overview")
    val overview: String?,
    @SerialName("genres")
    val genres: List<NetworkGenresItem>,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int,
)
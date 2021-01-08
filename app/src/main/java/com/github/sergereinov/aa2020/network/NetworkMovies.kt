package com.github.sergereinov.aa2020.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkMovies(
    val results: List<NetworkMovieItem>,

// *** skip unused fields ***
//	val page: Int,
//	@SerialName("total_pages")
//	val totalPages: Int,
//	@SerialName("total_results")
//	val totalResults: Int
)

@Serializable
data class NetworkMovieItem(
    val id: Int,
    val adult: Boolean,
    val title: String,

    @SerialName("genre_ids")
    val genreIds: List<Int>,

    @SerialName("poster_path")
    val posterPath: String?,

    @SerialName("vote_average")
    val voteAverage: Double,

    @SerialName("vote_count")
    val voteCount: Int,

// *** skip unused fields ***
//	val overview: String,
//	val originalLanguage: String,
//	val originalTitle: String,
//	val video: Boolean,
//	val backdropPath: String,
//	val releaseDate: String,
//	val popularity: Double,
)
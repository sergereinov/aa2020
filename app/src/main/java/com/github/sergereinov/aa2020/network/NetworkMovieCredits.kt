package com.github.sergereinov.aa2020.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*
	Query: GET /movie/{movie_id}/credits
	Schema: https://developers.themoviedb.org/3/movies/get-movie-credits
	data class NetworkMovieCredits
 */

@Serializable
data class NetworkMovieCredits(
	@SerialName("id")
	val id: Int,
	@SerialName("cast")
	val cast: List<NetworkCastItem>,
)

@Serializable
data class NetworkCastItem(
	@SerialName("id")
	val id: Int,
	@SerialName("name")
	val name: String,
	@SerialName("profile_path")
	val profilePath: String?,
)
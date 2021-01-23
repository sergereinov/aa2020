package com.github.sergereinov.aa2020.network

import kotlinx.serialization.Serializable

@Serializable
data class NetworkGenres(
	val genres: List<NetworkGenresItem>
)

@Serializable
data class NetworkGenresItem(
	val id: Int,
	val name: String,
)
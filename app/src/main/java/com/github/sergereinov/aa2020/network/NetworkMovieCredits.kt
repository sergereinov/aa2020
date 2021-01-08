package com.github.sergereinov.aa2020.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkMovieCredits(
	val id: Int,
	val cast: List<NetworkCastItem>,

// *** skip unused fields ***
//	val crew: List<NetworkCrewItem>
)

@Serializable
data class NetworkCastItem(
	val id: Int,
	val name: String,

	@SerialName("profile_path")
	val profilePath: String?,

// *** skip unused fields ***
//	val castId: Int,
//	val character: String,
//	val gender: Int,
//	val creditId: String,
//	val knownForDepartment: String,
//	val originalName: String,
//	val popularity: Double,
//	val adult: Boolean,
//	val order: Int
)

// *** skip unused types ***
//@Serializable
//data class NetworkCrewItem(
//	val gender: Int,
//	val creditId: String,
//	val knownForDepartment: String,
//	val originalName: String,
//	val popularity: Double,
//	val name: String,
//	val profilePath: String?,
//	val id: Int,
//	val adult: Boolean,
//	val department: String,
//	val job: String
//)
package com.github.sergereinov.aa2020.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkMovieDetails(
    val id: Int,
    val adult: Boolean,
    val title: String,
    val overview: String?,
    val genres: List<NetworkGenresItem>,

    @SerialName("backdrop_path")
    val backdropPath: String?,

    @SerialName("vote_average")
    val voteAverage: Double,

    @SerialName("vote_count")
    val voteCount: Int,

// *** skip unused fields ***
//	val originalLanguage: String? = null,
//	val imdbId: String? = null,
//	val video: Boolean? = null,
//	val revenue: Int? = null,
//	val popularity: Double? = null,
//	val productionCountries: List<ProductionCountriesItem?>? = null,
//	val budget: Int? = null,
//	val originalTitle: String? = null,
//	val runtime: Int? = null,
//	val posterPath: String? = null,
//	val spokenLanguages: List<SpokenLanguagesItem?>? = null,
//	val productionCompanies: List<ProductionCompaniesItem?>? = null,
//	val releaseDate: String? = null,
//	val belongsToCollection: BelongsToCollection? = null,
//	val tagline: String? = null,
//	val homepage: String? = null,
//	val status: String? = null
)

// *** skip unused types ***
//data class ProductionCompaniesItem(
//	val logoPath: String? = null,
//	val name: String? = null,
//	val id: Int? = null,
//	val originCountry: String? = null
//)
//data class SpokenLanguagesItem(
//	val name: String? = null,
//	val iso6391: String? = null,
//	val englishName: String? = null
//)
//data class ProductionCountriesItem(
//	val iso31661: String? = null,
//	val name: String? = null
//)
//data class BelongsToCollection(
//	val backdropPath: String? = null,
//	val name: String? = null,
//	val id: Int? = null,
//	val posterPath: String? = null
//)
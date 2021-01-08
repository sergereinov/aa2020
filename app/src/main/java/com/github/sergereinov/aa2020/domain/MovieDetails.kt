package com.github.sergereinov.aa2020.domain

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String?,
    val minimumAge: Int,
    val genres: List<Genre>,
    val backdrop: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val actors: List<Actor>
)
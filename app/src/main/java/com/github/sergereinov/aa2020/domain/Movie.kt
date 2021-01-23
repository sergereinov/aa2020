package com.github.sergereinov.aa2020.domain

data class Movie(
    val id: Int,
    val title: String,
    val minimumAge: Int,
    val genres: List<Genre>,
    val poster: String?,
    val voteAverage: Double,
    val voteCount: Int,
)
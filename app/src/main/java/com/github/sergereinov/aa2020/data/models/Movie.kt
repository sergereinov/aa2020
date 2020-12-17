package com.github.sergereinov.aa2020.data.models

//simplified model with 'tags', 'reviews' and 'movieLen' as texts

data class Movie(
    val id: Int,
    val movieImageId: Int,
    val pg: String,
    val isLiked: Boolean,
    val tags: String,
    val starsCount: Int,
    val reviews: String,
    val title: String,
    val movieLen: String
)

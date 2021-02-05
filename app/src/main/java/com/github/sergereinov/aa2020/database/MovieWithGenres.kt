package com.github.sergereinov.aa2020.database

import androidx.room.*

data class MovieWithGenres(
    @Embedded
    val movie: MovieEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "movie_id"
    )
    val genres: List<GenreEntity>
)
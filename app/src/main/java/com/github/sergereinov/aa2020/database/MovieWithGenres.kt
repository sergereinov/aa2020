package com.github.sergereinov.aa2020.database

import androidx.room.*

data class MovieWithGenres(
    @Embedded
    val movie: MovieEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "genre_id",
        associateBy = Junction(
            value = MovieGenreCrossRef::class,
            parentColumn = "movie_id",
            entityColumn = "genre_id"
        )
    )
    val genres: List<GenreEntity>
)

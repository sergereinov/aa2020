package com.github.sergereinov.aa2020.database

import androidx.room.Embedded
import androidx.room.Relation

data class MovieWithGenresAndActors(
    @Embedded
    val movie: MovieEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "movie_id"
    )
    val genres: List<GenreEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "movie_id"
    )
    val actors: List<ActorEntity>
)
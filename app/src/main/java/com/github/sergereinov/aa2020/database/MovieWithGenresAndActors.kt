package com.github.sergereinov.aa2020.database

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class MovieWithGenresAndActors(
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
    val genres: List<GenreEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "actor_id",
        associateBy = Junction(
            value = MovieActorCrossRef::class,
            parentColumn = "movie_id",
            entityColumn = "actor_id"
        )
    )
    val actors: List<ActorEntity>
)

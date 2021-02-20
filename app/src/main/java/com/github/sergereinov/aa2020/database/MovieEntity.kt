package com.github.sergereinov.aa2020.database

import androidx.room.*

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "overview")
    val overview: String?,
    @ColumnInfo(name = "min_age")
    val minimumAge: Int,
    @ColumnInfo(name = "backdrop_url")
    val backdrop: String?,
    @ColumnInfo(name = "poster_url")
    val poster: String?,
    @ColumnInfo(name = "vote_avg")
    val voteAverage: Double,
    @ColumnInfo(name = "vote_count")
    val voteCount: Int,
)

data class MoviePartialEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val movieId: Long,
    @ColumnInfo(name = "overview")
    val overview: String?,
    @ColumnInfo(name = "backdrop_url")
    val backdrop: String?,
)

@Entity(
    tableName = "movie_genre_cross_ref",
    primaryKeys = ["movie_id", "genre_id"],
    indices = [
        Index(value = ["movie_id"]),
        Index(value = ["genre_id"])
    ]
)
data class MovieGenreCrossRef(
    @ColumnInfo(name = "movie_id")
    val movieId: Long,
    @ColumnInfo(name = "genre_id")
    val genreId: Long
)

@Entity(
    tableName = "movie_actor_cross_ref",
    primaryKeys = ["movie_id", "actor_id"],
    indices = [
        Index(value = ["movie_id"]),
        Index(value = ["actor_id"])
    ]
)
data class MovieActorCrossRef(
    @ColumnInfo(name = "movie_id")
    val movieId: Long,
    @ColumnInfo(name = "actor_id")
    val actorId: Long
)

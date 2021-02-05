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
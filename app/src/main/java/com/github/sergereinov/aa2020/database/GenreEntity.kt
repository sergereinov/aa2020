package com.github.sergereinov.aa2020.database

import androidx.room.*

@Entity(
    tableName = "genre",
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movie_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["movie_id"]),
    ]
)
data class GenreEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "movie_id")
    val movieId: Long,
    @ColumnInfo(name = "genre_id")
    val genreId: Long,
    @ColumnInfo(name = "name")
    val name: String
)
package com.github.sergereinov.aa2020.database

import androidx.room.*

@Entity(tableName = "genre")
data class GenreEntity(
    @PrimaryKey
    @ColumnInfo(name = "genre_id")
    val genreId: Long,
    @ColumnInfo(name = "name")
    val name: String
)

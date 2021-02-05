package com.github.sergereinov.aa2020.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "actor",
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movie_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        androidx.room.Index(value = ["movie_id"]),
    ]
)
data class ActorEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "movie_id")
    val movieId: Long,
    @ColumnInfo(name = "actor_id")
    val actorId: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "picture_url")
    val picture: String?
)
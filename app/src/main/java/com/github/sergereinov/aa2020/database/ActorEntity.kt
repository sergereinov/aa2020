package com.github.sergereinov.aa2020.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "actor")
data class ActorEntity(
    @PrimaryKey
    @ColumnInfo(name = "actor_id")
    val actorId: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "picture_url")
    val picture: String?
)

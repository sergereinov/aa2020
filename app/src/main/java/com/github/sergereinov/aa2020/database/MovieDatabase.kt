package com.github.sergereinov.aa2020.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        MovieEntity::class,
        GenreEntity::class,
        ActorEntity::class,
        MovieGenreCrossRef::class,
        MovieActorCrossRef::class
    ],
    version = 2,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract val movieDao: MovieDao

    companion object {
        fun create(applicationContext: Context): MovieDatabase = Room.databaseBuilder(
            applicationContext,
            MovieDatabase::class.java,
            "movies"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
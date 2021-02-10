package com.github.sergereinov.aa2020.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert
    fun insertMovies(movies: List<MovieEntity>)

    @Insert
    fun insertGenres(genres: List<GenreEntity>)

    @Insert
    fun insertActors(actors: List<ActorEntity>)

    @Query("DELETE FROM actor WHERE movie_id=:movieId")
    fun deleteActorsByMovie(movieId: Long)

    @Update(entity = MovieEntity::class)
    fun updateMovieWithPartial(partial: MoviePartialEntity)

    @Query("DELETE FROM movie")
    fun deleteAll()

    @Transaction
    fun replaceMoviesAndGenres(movies: List<MovieEntity>, genres: List<GenreEntity>) {
        deleteAll()
        insertMovies(movies)
        insertGenres(genres)
    }

    @Transaction
    fun updateMovieDetailsAndActors(partial: MoviePartialEntity, movieActors: List<ActorEntity>) {
        updateMovieWithPartial(partial)
        deleteActorsByMovie(partial.movieId)
        insertActors(movieActors)
    }

    @Transaction
    @Query("SELECT * FROM movie")
    fun getMoviesWithGenresFlow(): Flow<List<MovieWithGenres>>

    @Transaction
    @Query("SELECT * FROM movie WHERE id=:movieId")
    fun getMovieWithGenresAndActorsFlow(movieId: Long): Flow<MovieWithGenresAndActors>

}
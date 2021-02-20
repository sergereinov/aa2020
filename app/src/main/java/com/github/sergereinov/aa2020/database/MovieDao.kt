package com.github.sergereinov.aa2020.database

import androidx.room.*

@Dao
interface MovieDao {
    @Insert
    fun insertMovies(movies: List<MovieEntity>)

    @Query("DELETE FROM movie")
    fun deleteMovies()

    @Insert
    fun insertGenres(genres: List<GenreEntity>)

    @Query("DELETE FROM genre")
    fun deleteGenres()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertActors(actors: List<ActorEntity>)

    @Query("DELETE FROM actor")
    fun deleteActors()

    @Insert
    fun insertMovieGenreCrossRefs(mgCrossRefs: List<MovieGenreCrossRef>)

    @Query("DELETE FROM movie_genre_cross_ref")
    fun deleteMovieGenreCrossRefs()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovieActorCrossRefs(maCrossRefs: List<MovieActorCrossRef>)

    @Query("DELETE FROM movie_actor_cross_ref")
    fun deleteMovieActorCrossRefs()

    @Transaction
    fun replaceMoviesAndGenres(
        movies: List<MovieEntity>,
        genres: List<GenreEntity>,
        mgCrossRefs: List<MovieGenreCrossRef>
    ) {
        deleteActors()
        deleteGenres()
        deleteMovieActorCrossRefs()
        deleteMovieGenreCrossRefs()
        deleteMovies()

        insertGenres(genres)
        insertMovies(movies)
        insertMovieGenreCrossRefs(mgCrossRefs)
    }

    @Update(entity = MovieEntity::class)
    fun updateMovieWithPartial(partial: MoviePartialEntity)

    @Transaction
    fun updateMovieDetailsAndActors(
        partial: MoviePartialEntity,
        movieActors: List<ActorEntity>,
        maCrossRefs: List<MovieActorCrossRef>
    ) {
        updateMovieWithPartial(partial)
        insertActors(movieActors)
        insertMovieActorCrossRefs(maCrossRefs)
    }

    @Transaction
    @Query("SELECT * FROM movie")
    fun getMoviesWithGenres(): List<MovieWithGenres>

    @Transaction
    @Query("SELECT * FROM movie WHERE id=:movieId")
    fun getMovieWithGenresAndActors(movieId: Long): MovieWithGenresAndActors
}

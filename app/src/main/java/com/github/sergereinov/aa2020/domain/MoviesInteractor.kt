package com.github.sergereinov.aa2020.domain

import com.github.sergereinov.aa2020.database.*
import com.github.sergereinov.aa2020.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MoviesInteractor(
    private val networkInteractor: INetworkInteractor,
    database: MovieDatabase,
    private val notifications: Notifications
) : IMoviesInteractor {

    private val movieDao = database.movieDao

    override fun moviesFlow(): Flow<List<Movie>> =
        movieDao.getMoviesWithGenresFlow().map { it.toDomainMovies() }

    override fun detailsFlow(movieId: Int): Flow<MovieDetails> =
        movieDao
            .getMovieWithGenresAndActorsFlow(movieId.toLong())
            .filter { it != null }
            .map { it!!.toDomainMovieDetails() }

    override suspend fun refreshMovies() {
        val netGenres = networkInteractor.loadGenres()
        /*
            Switch from one to another to test/debug for the New Film notification bubble
            val netMovies = networkInteractor.loadPopularMovies()
            val netMovies = networkInteractor.loadTopRatedMovies()
         */
        val netMovies = networkInteractor.loadPopularMovies()

        val newMaxVotedMovie = withContext(Dispatchers.IO) {

            val oldMovies = movieDao.getMovies()

            movieDao.replaceMoviesAndGenres(
                netMovies.toDomainMovies(),
                netGenres.toDomainGenres(),
                netMovies.toDomainMovieGenreCrossRefs()
            )

            movieDao
                .getMovies()
                .filter { m -> oldMovies.none { old -> old.id == m.id } }
                .maxByOrNull { m -> m.voteAverage }
        }

        newMaxVotedMovie?.let { movie ->
            notifications.showNotification(movie)
        }
    }

    override suspend fun refreshMovieDetails(movieId: Int) {
        val netDetails = networkInteractor.loadMovieDetails(movieId)
        val netCredits = networkInteractor.loadMovieCredits(movieId)

        val maxActorsCount = 10

        withContext(Dispatchers.IO) {
            movieDao.updateMovieDetailsAndActors(
                netDetails.toDomainMoviePartial(),
                netCredits.toDomainActors(maxActorsCount),
                netCredits.toDomainMovieActorCrossRefs(movieId.toLong(), maxActorsCount)
            )
        }
    }

    // *** helpers ******************************************************

    private fun List<GenreEntity>.toDomainGenres(): List<Genre> = map {
        Genre(
            id = it.genreId.toInt(),
            name = it.name
        )
    }

    private fun List<ActorEntity>.toDomainActors(): List<Actor> = map {
        Actor(
            id = it.actorId,
            name = it.name,
            picture = it.picture
        )
    }

    private fun List<MovieWithGenres>.toDomainMovies(): List<Movie> = map {
        Movie(
            id = it.movie.id.toInt(),
            title = it.movie.title,
            minimumAge = it.movie.minimumAge,
            genres = it.genres.toDomainGenres(),
            poster = it.movie.poster,
            voteAverage = it.movie.voteAverage,
            voteCount = it.movie.voteCount
        )
    }

    private fun MovieWithGenresAndActors.toDomainMovieDetails() =
        MovieDetails(
            id = this.movie.id.toInt(),
            title = this.movie.title,
            overview = this.movie.overview,
            minimumAge = this.movie.minimumAge,
            genres = this.genres.toDomainGenres(),
            backdrop = this.movie.backdrop,
            voteAverage = this.movie.voteAverage,
            voteCount = this.movie.voteCount,
            actors = this.actors.toDomainActors()
        )

    private fun NetworkMovies.toDomainMovies(): List<MovieEntity> = results.map { netMovie ->
        MovieEntity(
            id = netMovie.id.toLong(),
            title = netMovie.title,
            overview = null,
            minimumAge = getMinimumAge(netMovie.adult),
            backdrop = null,
            poster = Server.getPosterUrl(netMovie.posterPath),
            voteAverage = netMovie.voteAverage,
            voteCount = netMovie.voteCount
        )
    }

    private fun NetworkMovies.toDomainMovieGenreCrossRefs(): List<MovieGenreCrossRef> =
        results.flatMap { netMovie ->
            netMovie.genreIds.map { genreId ->
                MovieGenreCrossRef(
                    movieId = netMovie.id.toLong(),
                    genreId = genreId.toLong()
                )
            }
        }

    private fun NetworkGenres.toDomainGenres(): List<GenreEntity> = genres.map { netGenre ->
        GenreEntity(
            genreId = netGenre.id.toLong(),
            name = netGenre.name
        )
    }

    private fun NetworkMovieDetails.toDomainMoviePartial(): MoviePartialEntity = MoviePartialEntity(
        movieId = id.toLong(),
        overview = overview,
        backdrop = Server.getBackdropUrl(backdropPath)
    )

    private fun NetworkMovieCredits.toDomainActors(takeN: Int): List<ActorEntity> =
        cast.take(takeN).map { netCast ->
            ActorEntity(
                actorId = netCast.id,
                name = netCast.name,
                picture = Server.getActorPictureUrl(netCast.profilePath)
            )
        }

    private fun NetworkMovieCredits.toDomainMovieActorCrossRefs(
        movieId: Long,
        takeN: Int
    ): List<MovieActorCrossRef> =
        cast.take(takeN).map { netCast ->
            MovieActorCrossRef(
                movieId = movieId,
                actorId = netCast.id.toLong()
            )
        }

    private fun getMinimumAge(adult: Boolean) = if (adult) 16 else 13
}

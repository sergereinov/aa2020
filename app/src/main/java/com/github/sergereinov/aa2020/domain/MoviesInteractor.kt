package com.github.sergereinov.aa2020.domain

import com.github.sergereinov.aa2020.database.*
import com.github.sergereinov.aa2020.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesInteractor(
    private val networkInteractor: INetworkInteractor,
    database: MovieDatabase,
) : IMoviesInteractor {

    private val movieDao = database.movieDao

    override suspend fun getCachedMovies(): List<Movie> = withContext(Dispatchers.IO) {
        val dbMovies = movieDao.getMoviesWithGenres()
        dbMovies.toDomainMovies()
    }

    override suspend fun getCachedDetails(movieId: Int): MovieDetails =
        withContext(Dispatchers.IO) {
            val dbMovie = movieDao.getMovieWithGenresAndActors(movieId.toLong())
            dbMovie.toDomainMovieDetails()
        }

    override suspend fun loadMovies(): List<Movie> {
        val netGenres = networkInteractor.loadGenres()
        val netMovies = networkInteractor.loadPopularMovies()

        withContext(Dispatchers.IO) {
            movieDao.replaceMoviesAndGenres(
                netMovies.toDomainMovies(),
                netGenres.toDomainGenres(),
                netMovies.toDomainMovieGenreCrossRefs()
            )
        }

        return getCachedMovies()
    }

    override suspend fun loadMovieDetails(movieId: Int): MovieDetails {
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

        return getCachedDetails(movieId)
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

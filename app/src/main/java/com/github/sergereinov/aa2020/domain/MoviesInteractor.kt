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

        val dbMoviesAndGenres = buildDatabaseMoviesAndGenres(netMovies, netGenres)

        withContext(Dispatchers.IO) {
            movieDao.replaceMoviesAndGenres(dbMoviesAndGenres.movies, dbMoviesAndGenres.genres)
        }

        return getCachedMovies()
    }

    override suspend fun loadMovieDetails(movieId: Int): MovieDetails {
        val netDetails = networkInteractor.loadMovieDetails(movieId)
        val netCredits = networkInteractor.loadMovieCredits(movieId)

        val dbPartialAndActors = buildDatabasePartialAndActors(netDetails, netCredits)

        withContext(Dispatchers.IO) {
            movieDao.updateMovieDetailsAndActors(
                dbPartialAndActors.partial,
                dbPartialAndActors.actors
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

    private data class DatabaseMoviesAndGenres(
        val movies: List<MovieEntity>,
        val genres: List<GenreEntity>
    )

    private fun buildDatabaseMoviesAndGenres(
        netMovies: NetworkMovies,
        netGenres: NetworkGenres
    ): DatabaseMoviesAndGenres {
        val dbMovies = mutableListOf<MovieEntity>()
        val dbGenres = mutableListOf<GenreEntity>()

        val netGenresMap = netGenres.genres.map { it.id to it }.toMap()

        netMovies.results.forEach { netMovie ->
            dbMovies.add(
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
            )

            netMovie.genreIds
                .mapNotNull { genreId -> netGenresMap[genreId] }
                .forEach { netGenre ->
                    dbGenres.add(
                        GenreEntity(
                            movieId = netMovie.id.toLong(),
                            genreId = netGenre.id.toLong(),
                            name = netGenre.name
                        )
                    )
                }
        }

        return DatabaseMoviesAndGenres(dbMovies, dbGenres)
    }

    private data class DatabasePartialAndActors(
        val partial: MoviePartialEntity,
        val actors: List<ActorEntity>
    )

    private fun buildDatabasePartialAndActors(
        netDetails: NetworkMovieDetails,
        netCredits: NetworkMovieCredits
    ): DatabasePartialAndActors {
        val dbPartial = MoviePartialEntity(
            movieId = netDetails.id.toLong(),
            overview = netDetails.overview,
            backdrop = Server.getBackdropUrl(netDetails.backdropPath)
        )

        val dbActors = netCredits.cast.take(10).map {
            ActorEntity(
                movieId = netDetails.id.toLong(),
                actorId = it.id,
                name = it.name,
                picture = Server.getActorPictureUrl(it.profilePath)
            )
        }

        return DatabasePartialAndActors(dbPartial, dbActors)
    }

    private fun getMinimumAge(adult: Boolean) = if (adult) 16 else 13
}
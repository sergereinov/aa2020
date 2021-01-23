package com.github.sergereinov.aa2020.domain

import com.github.sergereinov.aa2020.network.*

class MoviesInteractor(
    private val networkInteractor: INetworkInteractor
) : IMoviesInteractor {

    override suspend fun loadMovies(): List<Movie> {
        val netGenres = networkInteractor.loadGenres()
        val netMovies = networkInteractor.loadPopularMovies()

        val genresMap = netGenres.genres.toGenres().map { it.id to it }.toMap()

        return netMovies.toMovies(genresMap)
    }

    override suspend fun loadMovieDetails(movieId: Int): MovieDetails {
        val netDetails = networkInteractor.loadMovieDetails(movieId)
        val netCredits = networkInteractor.loadMovieCredits(movieId)

        return netDetails.toMovieDetails(netCredits)
    }

    // *** helpers ******************************************************

    private fun List<NetworkGenresItem>.toGenres(): List<Genre> = map {
        Genre(it.id, it.name)
    }

    private fun Map<Int, Genre>.buildListByIDs(ids: List<Int>): List<Genre> = ids.mapNotNull { id ->
        this[id]
    }

    private fun getMinimumAge(adult: Boolean) = if (adult) 16 else 13

    private fun NetworkMovies.toMovies(genresMap: Map<Int, Genre>): List<Movie> = this.results.map {
        Movie(
            id = it.id,
            title = it.title,
            minimumAge = getMinimumAge(it.adult),
            genres = genresMap.buildListByIDs(it.genreIds),
            poster = Server.getPosterUrl(it.posterPath),
            voteAverage = it.voteAverage,
            voteCount = it.voteCount
        )
    }

    private fun NetworkMovieCredits.toActorsTopN(count: Int = 10): List<Actor> =
        this.cast.take(count).map {
            Actor(
                it.id,
                it.name,
                Server.getActorPictureUrl(it.profilePath)
            )
        }

    private fun NetworkMovieDetails.toMovieDetails(credits: NetworkMovieCredits) = MovieDetails(
        id = id,
        title = title,
        overview = overview,
        minimumAge = getMinimumAge(adult),
        genres = genres.toGenres(),
        backdrop = Server.getBackdropUrl(backdropPath),
        voteAverage = voteAverage,
        voteCount = voteCount,
        actors = credits.toActorsTopN(10),
    )
}
package com.github.sergereinov.aa2020.domain

import com.github.sergereinov.aa2020.R
import com.github.sergereinov.aa2020.data.models.Actor
import com.github.sergereinov.aa2020.data.models.MovieDetails

class MovieDetailsDataSource {
    companion object {
        fun getDetails(movieId: Int?): MovieDetails? {
            return when (movieId) {
                1 -> MovieDetails(
                        actors = listOf(
                                Actor(1, R.drawable.actor1, "Robert Downey Jr."),
                                Actor(2, R.drawable.actor2, "Chris Evans"),
                                Actor(3, R.drawable.actor3, "Mark Ruffalo"),
                                Actor(4, R.drawable.actor4, "Chris Hemsworth"),
                        )
                )
                2 -> MovieDetails(
                        actors = listOf(
                                Actor(2, R.drawable.actor2, "Chris Evans"),
                                Actor(3, R.drawable.actor3, "Mark Ruffalo"),
                        )
                )
                3 -> MovieDetails(
                        actors = listOf(
                                Actor(4, R.drawable.actor4, "Chris Hemsworth"),
                        )
                )
                4 -> MovieDetails(actors = listOf())
                else -> null
            }
        }
    }
}
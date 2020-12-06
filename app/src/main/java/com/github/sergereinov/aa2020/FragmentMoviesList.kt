package com.github.sergereinov.aa2020

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.github.sergereinov.aa2020.data.models.Movie
import java.util.*

class FragmentMoviesList : Fragment() {

    private var listener: FragmentClicks? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieClickListener = MoviesListAdapter.ClickListener { _ ->
            listener?.filmCard()
        }
        val adapter = MoviesListAdapter(movieClickListener)
        val recyclerView = view.findViewById<RecyclerView>(R.id.movies_list)
        recyclerView.adapter = adapter

        adapter.submitList(moviesList)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as FragmentClicks?
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface FragmentClicks {
        fun filmCard()
    }

    companion object {
        private val moviesList = listOf(
                Movie(
                        id = 1,
                        movieImageId = R.drawable.movie,
                        pg = "13+",
                        isLiked = false,
                        tags = "Action, Adventure, Drama",
                        starsCount = 4,
                        reviews = "125 Reviews",
                        title = "Avengers: End Game",
                        movieLen = "137 min"
                ),
                Movie(
                        id = 2,
                        movieImageId = R.drawable.movie2,
                        pg = "16+",
                        isLiked = true,
                        tags = "Action, Sci-Fi, Thriller",
                        starsCount = 5,
                        reviews = "98 Reviews",
                        title = "Tenet",
                        movieLen = "97 min"
                ),
                Movie(
                        id = 3,
                        movieImageId = R.drawable.movie3,
                        pg = "13+",
                        isLiked = false,
                        tags = "Action, Adventure, Sci-Fi",
                        starsCount = 4,
                        reviews = "38 Reviews",
                        title = "Black Widow",
                        movieLen = "102 min"
                ),
                Movie(
                        id = 4,
                        movieImageId = R.drawable.movie4,
                        pg = "13+",
                        isLiked = false,
                        tags = "Action, Adventure, Fantasy",
                        starsCount = 5,
                        reviews = "74 Reviews",
                        title = "Wonder Woman 1984",
                        movieLen = "120 min"
                )
        )
    }
}
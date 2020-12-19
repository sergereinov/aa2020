package com.github.sergereinov.aa2020

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.github.sergereinov.aa2020.domain.MovieDataSource
import kotlinx.coroutines.*

class FragmentMoviesList : Fragment() {

    private var listener: FragmentClicks? = null
    private val uiScope = CoroutineScope(Dispatchers.Main + Job())

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MoviesListAdapter { movie ->
            listener?.filmCard(movie.id)
        }
        val recyclerView = view.findViewById<RecyclerView>(R.id.movies_list)
        recyclerView.adapter = adapter

        uiScope.launch {
            MovieDataSource.loadMovies(requireContext())
            adapter.submitList(MovieDataSource.getMovies())
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as FragmentClicks?
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        uiScope.coroutineContext.cancelChildren()
    }


    interface FragmentClicks {
        fun filmCard(movieId: Int)
    }
}
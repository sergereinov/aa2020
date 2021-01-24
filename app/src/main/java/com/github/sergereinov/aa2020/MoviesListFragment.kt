package com.github.sergereinov.aa2020

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.github.sergereinov.aa2020.domain.MoviesInteractor

class MoviesListFragment : Fragment() {

    private val viewModel: MoviesListViewModel by viewModels {
        MoviesListViewModelFactory(
            MoviesInteractor(
                (requireActivity().application as MoviesApplication).networkModule,
                (requireActivity().application as MoviesApplication).database
            )
        )
    }
    private var listener: FragmentClicks? = null

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

        viewModel.movies.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        viewModel.errorLoadingMovies.observe(viewLifecycleOwner, { error ->
            error?.let {
                Toast.makeText(
                    context,
                    getString(R.string.error_loading_movies).format(it),
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.doneWithErrorLoadingMovies()
            }
        })
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
        fun filmCard(movieId: Int)
    }
}
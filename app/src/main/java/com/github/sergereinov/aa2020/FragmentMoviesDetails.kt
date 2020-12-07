package com.github.sergereinov.aa2020

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.github.sergereinov.aa2020.data.models.Actor
import com.github.sergereinov.aa2020.domain.MovieDetailsDataSource

class FragmentMoviesDetails : Fragment() {

    private var listener: FragmentClicks? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.back)?.setOnClickListener {
            listener?.backFromMovieDetails()
        }

        val adapter = ActorsListAdapter()
        val actorsView = view.findViewById<RecyclerView>(R.id.actors_list)
        actorsView?.adapter = adapter

        val movieId = arguments?.getInt(PARAM_MOVIE_ID, 0)
        MovieDetailsDataSource.getDetails(movieId)?.let {

            //fill details

            if (it.actors.isNotEmpty()) {
                adapter.submitList(it.actors)

                view.findViewById<View>(R.id.no_actors).visibility = View.GONE
                actorsView.visibility = View.VISIBLE
            }
        }

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
        fun backFromMovieDetails()
    }

    companion object {
        private const val PARAM_MOVIE_ID = "movie_id"

        fun newInstance(movieId: Int) = FragmentMoviesDetails().also {
            val args = Bundle()
            args.putInt(PARAM_MOVIE_ID, movieId)
            it.arguments = args
        }

    }

}
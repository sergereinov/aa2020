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
        view.findViewById<RecyclerView>(R.id.actors_list)?.adapter = adapter

        adapter.submitList(actorList)
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
        private val actorList = listOf(
                Actor(
                        id = 1,
                        imageId = R.drawable.actor1,
                        name = "Robert Downey Jr."
                ),
                Actor(
                        id = 2,
                        imageId = R.drawable.actor2,
                        name = "Chris Evans"
                ),
                Actor(
                        id = 3,
                        imageId = R.drawable.actor3,
                        name = "Mark Ruffalo"
                ),
                Actor(
                        id = 4,
                        imageId = R.drawable.actor4,
                        name = "Chris Hemsworth"
                )
        )
    }
}
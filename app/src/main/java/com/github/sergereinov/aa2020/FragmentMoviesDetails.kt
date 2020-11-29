package com.github.sergereinov.aa2020

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

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
}
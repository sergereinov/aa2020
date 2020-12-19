package com.github.sergereinov.aa2020

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.sergereinov.aa2020.domain.MovieDataSource

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
        MovieDataSource.getMovie(movieId)?.let {

            val backdropImage: ImageView = view.findViewById(R.id.title_image)
            val pgText: TextView = view.findViewById(R.id.pg_text)
            val titleText: TextView = view.findViewById(R.id.title_text)
            val tagText: TextView = view.findViewById(R.id.tag_text)
            val reviewsText: TextView = view.findViewById(R.id.text_movie_reviews)
            val storylineText: TextView = view.findViewById(R.id.storyline_text)
            val starsImages: List<ImageView> = listOf(
                view.findViewById(R.id.star1_image),
                view.findViewById(R.id.star2_image),
                view.findViewById(R.id.star3_image),
                view.findViewById(R.id.star4_image),
                view.findViewById(R.id.star5_image)
            )

            //fill details

            Glide.with(requireContext())
                .load(it.backdrop)
                .placeholder(R.mipmap.ic_banner_loading)
                .fitCenter()
                .into(backdropImage)

            pgText.text = "%d+".format(it.minimumAge)
            titleText.text = it.title
            tagText.text = it.genres.joinToString(", ") { it.name }
            reviewsText.text = "%d Reviews".format(it.numberOfRatings)
            storylineText.text = it.overview

            val starsCount = kotlin.math.floor(it.ratings / 2).toInt()
            starsImages.forEachIndexed { index, imageView ->
                val colorId = if (starsCount > index) R.color.red_star else R.color.blank_star
                ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(
                    ContextCompat.getColor(imageView.context, colorId)
                ))
            }

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
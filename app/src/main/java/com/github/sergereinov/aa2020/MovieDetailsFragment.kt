package com.github.sergereinov.aa2020

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.sergereinov.aa2020.domain.MoviesInteractor

class MovieDetailsFragment : Fragment() {

    private val viewModel: MovieDetailsViewModel by viewModels {
        MovieDetailsViewModelFactory(
            arguments?.getInt(PARAM_MOVIE_ID, 0) ?: 0,
            MoviesInteractor(
                (requireActivity().application as MoviesApplication).networkModule
            )
        )
    }
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

        //find views
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

        viewModel.dataMovie.observe(viewLifecycleOwner, { dataMovie ->
            dataMovie?.let { movie ->

                //fill details

                Glide.with(requireContext())
                    .load(movie.backdrop)
                    .placeholder(R.mipmap.ic_banner_loading)
                    .error(R.drawable.ic_no_image)
                    .fitCenter()
                    .into(backdropImage)

                pgText.text = requireContext().getString(R.string.pg_text).format(movie.minimumAge)
                titleText.text = movie.title
                tagText.text = movie.genres.joinToString(", ") { g -> g.name }
                reviewsText.text =
                    requireContext().getString(R.string.reviews_text).format(movie.voteCount)
                storylineText.text = movie.overview

                val starsCount = kotlin.math.floor(movie.voteAverage / 2).toInt()
                starsImages.forEachIndexed { index, imageView ->
                    val colorId = if (starsCount > index) R.color.red_star else R.color.blank_star
                    ImageViewCompat.setImageTintList(
                        imageView, ColorStateList.valueOf(
                            ContextCompat.getColor(imageView.context, colorId)
                        )
                    )
                }

                if (movie.actors.isNotEmpty()) {
                    adapter.submitList(movie.actors)

                    view.findViewById<View>(R.id.no_actors).visibility = View.GONE
                    actorsView.visibility = View.VISIBLE
                }
            }
        })

        viewModel.errorLoadingDetails.observe(viewLifecycleOwner, { error ->
            error?.let {
                Toast.makeText(
                    context,
                    "Error loading movie details: %s".format(it),
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.doneWithErrorLoadingDetails()
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
        fun backFromMovieDetails()
    }

    companion object {
        private const val PARAM_MOVIE_ID = "movie_id"

        fun newInstance(movieId: Int) = MovieDetailsFragment().also {
            val args = Bundle()
            args.putInt(PARAM_MOVIE_ID, movieId)
            it.arguments = args
        }
    }

}
package com.github.sergereinov.aa2020

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.sergereinov.aa2020.data.models.Movie
import java.util.*

class MoviesListAdapter(private val onClickCard: (item: Movie) -> Unit)
    : ListAdapter<Movie, MoviesListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.view_holder_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickCard)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val filmCard: View = itemView.findViewById(R.id.film_card)
        private val movieImage: ImageView = itemView.findViewById(R.id.movie_image)
        private val pgText: TextView = itemView.findViewById(R.id.pg)
        private val likeImage: ImageView = itemView.findViewById(R.id.like_image)
        private val tagText: TextView = itemView.findViewById(R.id.tag_text)
        private val starsImages: List<ImageView> = listOf(
                itemView.findViewById(R.id.star1_image),
                itemView.findViewById(R.id.star2_image),
                itemView.findViewById(R.id.star3_image),
                itemView.findViewById(R.id.star4_image),
                itemView.findViewById(R.id.star5_image)
        )
        private val reviewsText: TextView = itemView.findViewById(R.id.reviews_text)
        private val titleText: TextView = itemView.findViewById(R.id.title_text)
        private val movieLenText: TextView = itemView.findViewById(R.id.movie_len)

        fun bind(item: Movie, onClickCard: (item: Movie) -> Unit) {
            movieImage.setImageResource(item.movieImageId)

            pgText.text = item.pg
            tagText.text = item.tags
            reviewsText.text = item.reviews.toUpperCase(Locale.ROOT)
            titleText.text = item.title
            movieLenText.text = item.movieLen.toUpperCase(Locale.ROOT)

            //set ImageView tint
            //thx2: https://stackoverflow.com/questions/20121938/how-to-set-tint-for-an-image-view-programmatically-in-android/45571812#45571812
            if (item.isLiked) {
                ImageViewCompat.setImageTintList(likeImage, ColorStateList.valueOf(
                        ContextCompat.getColor(likeImage.context, R.color.red_star)
                ))
            }

            //set stars tint
            starsImages.forEachIndexed { index, imageView ->
                val colorId = if (item.starsCount > index) R.color.red_star else R.color.blank_star
                ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(
                        ContextCompat.getColor(imageView.context, colorId)
                ))
            }

            filmCard.setOnClickListener {
                onClickCard(item)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

}
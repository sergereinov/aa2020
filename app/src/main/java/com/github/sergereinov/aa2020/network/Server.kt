package com.github.sergereinov.aa2020.network

class Server {
    companion object {
        const val apiKey = "4501fc1549cae1a4f3ab48901d3425bf"
        const val apiUrl = "https://api.themoviedb.org/3/"

        //static demo conf from https://developers.themoviedb.org/3/configuration/get-api-configuration
        private const val imagesUrl = "https://image.tmdb.org/t/p/"
        private const val backdropPreferWidth = "w780" //backdrop for details screen
        private const val posterPreferWidth = "w500" //poster for list of a movies screen
        private const val profilePreferWidth = "w185" //actor's picture

        private fun getImageUrl(image: String?, preferWidth: String): String? {
            return if (image != null) imagesUrl + preferWidth + image else null
        }

        fun getBackdropUrl(image: String?) = getImageUrl(image, backdropPreferWidth)
        fun getPosterUrl(image: String?) = getImageUrl(image, posterPreferWidth)
        fun getActorPictureUrl(image: String?) = getImageUrl(image, profilePreferWidth)
    }
}
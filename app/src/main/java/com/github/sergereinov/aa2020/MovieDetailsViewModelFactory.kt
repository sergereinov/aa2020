package com.github.sergereinov.aa2020

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.sergereinov.aa2020.domain.IMoviesInteractor

class MovieDetailsViewModelFactory(
    private val movieId: Int,
    private val moviesInteractor: IMoviesInteractor
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MovieDetailsViewModel::class.java -> MovieDetailsViewModel(movieId, moviesInteractor)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}
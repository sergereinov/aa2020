package com.github.sergereinov.aa2020

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.sergereinov.aa2020.domain.IMoviesInteractor

class MoviesListViewModelFactory(
    private val moviesInteractor: IMoviesInteractor
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesListViewModel::class.java -> MoviesListViewModel(moviesInteractor)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}
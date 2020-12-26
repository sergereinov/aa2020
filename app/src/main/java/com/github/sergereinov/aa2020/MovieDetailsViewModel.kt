package com.github.sergereinov.aa2020

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.sergereinov.aa2020.data.Movie
import com.github.sergereinov.aa2020.domain.IMoviesInteractor
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val movieId: Int,
    private val moviesInteractor: IMoviesInteractor
) : ViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> get() = _movie

    init {
        viewModelScope.launch {
            when (val m = moviesInteractor.getMovie(movieId)) {
                null -> {
                    moviesInteractor.refresh()
                    _movie.value = moviesInteractor.getMovie(movieId)
                }
                else -> _movie.value = m
            }
        }
    }
}
package com.github.sergereinov.aa2020

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.sergereinov.aa2020.domain.IMoviesInteractor
import com.github.sergereinov.aa2020.domain.MovieDetails
import kotlinx.coroutines.*
import java.lang.Exception

class MovieDetailsViewModel(
    private val movieId: Int,
    private val moviesInteractor: IMoviesInteractor
) : ViewModel() {

    private val _errorLoadingDetails = MutableLiveData<String>()
    val errorLoadingDetails: LiveData<String> get() = _errorLoadingDetails
    fun doneWithErrorLoadingDetails() {
        _errorLoadingDetails.value = null
    }

    private val _movie = MutableLiveData<MovieDetails>()
    val dataMovie: LiveData<MovieDetails> get() = _movie

    init {
        viewModelScope.launch {
            try {
                _movie.value = moviesInteractor.loadMovieDetails(movieId)
            } catch (e: Exception) {
                _errorLoadingDetails.value = e.localizedMessage ?: e.message ?: e.toString()
            }
        }
    }
}
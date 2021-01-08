package com.github.sergereinov.aa2020

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.sergereinov.aa2020.domain.IMoviesInteractor
import com.github.sergereinov.aa2020.domain.Movie
import kotlinx.coroutines.*
import java.lang.Exception

class MoviesListViewModel(
    private val moviesInteractor: IMoviesInteractor
) : ViewModel() {

    private val uiScope = CoroutineScope(Job() + Dispatchers.Main)
    override fun onCleared() {
        super.onCleared()
        uiScope.cancel()
    }

    private val _errorLoadingMovies = MutableLiveData<String>()
    val errorLoadingMovies: LiveData<String> get() = _errorLoadingMovies
    fun doneWithErrorLoadingMovies() {
        _errorLoadingMovies.value = null
    }

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    init {
        uiScope.launch {
            try {
                _movies.value = moviesInteractor.loadMovies()
            } catch (e: Exception) {
                _errorLoadingMovies.value = e.localizedMessage ?: e.message ?: e.toString()
            }
        }
    }
}
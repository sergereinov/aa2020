package com.github.sergereinov.aa2020

import androidx.lifecycle.*
import com.github.sergereinov.aa2020.domain.IMoviesInteractor
import com.github.sergereinov.aa2020.domain.Movie
import kotlinx.coroutines.*
import java.lang.Exception

class MoviesListViewModel(
    private val moviesInteractor: IMoviesInteractor
) : ViewModel() {

    private val _errorLoadingMovies = MutableLiveData<String>()
    val errorLoadingMovies: LiveData<String> get() = _errorLoadingMovies
    fun doneWithErrorLoadingMovies() {
        _errorLoadingMovies.value = null
    }

    private val _movies = moviesInteractor
        .moviesFlow()
        .asLiveData(
            // Use Dispatchers.IO for DB work and
            // viewModelScope for auto cancellation
            Dispatchers.IO + viewModelScope.coroutineContext
        )

    val movies: LiveData<List<Movie>> get() = _movies

    init {
        viewModelScope.launch {
            try {
                moviesInteractor.refreshMovies()
            } catch (e: Exception) {
                _errorLoadingMovies.value = e.localizedMessage ?: e.message ?: e.toString()
            }
        }
    }
}

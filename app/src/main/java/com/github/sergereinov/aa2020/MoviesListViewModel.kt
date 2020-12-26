package com.github.sergereinov.aa2020

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.sergereinov.aa2020.data.Movie
import com.github.sergereinov.aa2020.domain.IMoviesInteractor
import kotlinx.coroutines.launch

class MoviesListViewModel(
    private val moviesInteractor: IMoviesInteractor
) : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    init {
        viewModelScope.launch {
            moviesInteractor.refresh()
            _movies.value = moviesInteractor.getMovies()
        }
    }
}
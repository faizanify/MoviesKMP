package com.habib.faizan.movieskmp.android

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habib.faizan.movieskmp.data.TopRatedMoviesEntry
import com.habib.faizan.movieskmp.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MoviesRepository = MoviesRepository()): ViewModel() {
    private val _movies = MutableStateFlow<List<TopRatedMoviesEntry>>(listOf())
    val movies = _movies.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMovies().collect{ movies->
                Log.d("MOVIESKMP-APP: ", "List Size: ${movies.size}")
                _movies.update {
                    it + movies
                }
            }
        }
    }

}
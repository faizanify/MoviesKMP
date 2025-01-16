package com.habib.faizan.movieskmp.repository

import com.habib.faizan.movieskmp.api.httpClient
import com.habib.faizan.movieskmp.data.TopRatedMoviesResponseDto
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import kotlinx.coroutines.flow.flow

const val API_KEY = "8d6d50438b5f40f31742da52eb12d3a8"
class MoviesRepository {

    private suspend fun getTopMovies(): TopRatedMoviesResponseDto {
        val response = httpClient.get {
            url("https://api.themoviedb.org/3/movie/top_rated")
            parameter("api_key", API_KEY)
        }
        return response.body()
    }

    fun getMovies() = flow {
        emit(getTopMovies().results)
    }
}
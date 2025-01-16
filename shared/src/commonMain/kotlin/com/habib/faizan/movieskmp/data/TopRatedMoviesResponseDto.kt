package com.habib.faizan.movieskmp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class TopRatedMoviesResponseDto(
    val page: Int,
    val results: List<TopRatedMoviesEntry>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)

@Serializable
data class TopRatedMoviesEntry(

    val adult: Boolean,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
//    @SerialName("genre_ids")
//    val genreIds: List<Int>? = null,

    val id: Int = 0,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("release_date")
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int
)


// Serialize Movies object to JSON string
fun serializeMovie(movie: TopRatedMoviesEntry): String {
    val json = Json { prettyPrint = true }
    return json.encodeToString(movie)
}

// Deserialize JSON string back to Movies object
fun deserializeMovie(jsonString: String): TopRatedMoviesEntry {
    val json = Json { ignoreUnknownKeys = true }
    return json.decodeFromString(jsonString)
}
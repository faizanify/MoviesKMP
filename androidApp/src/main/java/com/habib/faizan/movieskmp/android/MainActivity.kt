package com.habib.faizan.movieskmp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import coil.compose.rememberAsyncImagePainter
import com.habib.faizan.movieskmp.data.TopRatedMoviesEntry
import com.habib.faizan.movieskmp.data.serializeMovie

class MainActivity : ComponentActivity() {
    val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val movies = homeViewModel.movies.collectAsStateWithLifecycle()
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GridViewComposable(movies.value)
                }
            }
        }
    }
}

@Composable
fun GridViewComposable(moviesList: List<TopRatedMoviesEntry>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Two columns
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(moviesList.size) { index ->
            MovieItem(moviesList[index])
        }
    }
}

@Composable
fun MovieItem(movie: TopRatedMoviesEntry) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(220.dp),
        shape = MaterialTheme.shapes.medium,
//        elevation = CardElevation(4.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 2.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = "https://image.tmdb.org/t/p/original/${movie.posterPath}"),
                contentDescription = "",
                modifier = Modifier
                    .size(150.dp) // Fixed size for all images
                    .padding(bottom = 2.dp),
                contentScale = ContentScale.FillBounds
            )
            // Spacer to push text to the bottom
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = movie.title,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Rating: ${movie.voteAverage}",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun MoviesListComposable(moviesList: List<TopRatedMoviesEntry>, onMovieSelected: (String) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(10.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(moviesList, key = { movie -> movie.id.toString() }) { movie ->
            Card(
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth()
                    .clickable {
                        // Serialize the movie object to JSON and pass as argument
                        val serializedMovie = serializeMovie(movie)
                        onMovieSelected(serializedMovie)
                    }
            ) {
                Row {
                    Image(
                        painter = rememberAsyncImagePainter(model = "https://image.tmdb.org/t/p/w500/${movie.posterPath}"),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(16.dp)
                            .size(50.dp)
                    )
                    Column {
                        Text(text = movie.title)
                    }
                }
            }
        }
    }
}

@Composable
fun MoviesDetailsComposable() {
//    val movie = deserializeMovie(movieJson)
    Column {
        Text("details")
    }
}


// Navigation Graph setup
@Composable
fun NavigationSetup(value: List<TopRatedMoviesEntry>) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "list_screen") {
        composable("list_screen") {
            MoviesListComposable(moviesList = value, {
                navController.navigate("detail_screen")
            })
        }

        composable(
            "detail_screen",
            arguments = listOf(navArgument("movieJson") { type = NavType.StringType })
        ) { backStackEntry ->
//            val movieJson = backStackEntry.arguments?.getString("movieJson") ?: ""
//            val movie = deserializeMovie(movieJson)
            MoviesDetailsComposable()
        }
    }
}
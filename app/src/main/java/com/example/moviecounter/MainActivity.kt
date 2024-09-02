package com.example.moviecounter

import android.graphics.Movie
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviecounter.ui.theme.MovieCounterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieCounterTheme {
                var isShowingList by rememberSaveable {
                    mutableStateOf(false)
                }
                var movies by rememberSaveable {
                    mutableStateOf(listOf<String>())
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (isShowingList) {
                        MovieList(
                            modifier = Modifier.padding(innerPadding),
                            movies = movies,
                            onAddAnotherMovie = { isShowingList = false }
                        )
                    } else {
                        MovieCounter(
                            modifier = Modifier.padding(innerPadding),
                            onAddMovie = { movieName ->
                                movies = movies + movieName
                                isShowingList = true
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MovieCounter(modifier: Modifier = Modifier, onAddMovie: (String) -> Unit) {
    var count by rememberSaveable { mutableStateOf(0) }
    var movieName by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "You have added $count movies.")
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = movieName,
            onValueChange = { movieName = it },
            label = { Text("Movie Name") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (movieName.isNotBlank()){
                count++
                onAddMovie(movieName)
                movieName = ""
            }
        }) {
            Text(text = "Add Movie")
        }
    }
}

@Composable
fun MovieList(
    modifier: Modifier = Modifier,
    movies: List<String>,
    onAddAnotherMovie: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Movie List",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        movies.forEach { movie ->
            Text(
                text = movie,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = onAddAnotherMovie,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Add Movie")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewMovieCounter() {
    MovieCounter(onAddMovie = {})
}

@Preview(showBackground = true)
@Composable
fun PreviewMovieList(){
    MovieList(
        movies = listOf("Movie1", "Movie2", "Movie3"),
        onAddAnotherMovie = {}
    )
}
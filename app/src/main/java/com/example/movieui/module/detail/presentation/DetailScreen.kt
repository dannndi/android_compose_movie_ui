package com.example.movieui.module.detail.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.movieui.R
import com.example.movieui.core.route.AppRouteName
import com.example.movieui.core.theme.Gray
import com.example.movieui.core.theme.Yellow
import com.example.movieui.module.home.model.MovieModel

@Composable
fun DetailScreen(
    navController: NavHostController,
    movie: MovieModel,
) {
    val scrollState = rememberScrollState()

    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            Button(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Yellow
                ),
                shape = RoundedCornerShape(32.dp),
                onClick = {
                    navController.navigate(AppRouteName.SeatSelector)
                },
            ) {
                Text(text = "Booking Now")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Row(
                modifier = Modifier.padding(
                    horizontal = 16.dp, vertical = 8.dp
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back Button")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "Movie Detail", style = MaterialTheme.typography.h6)
            }
            Row(
                modifier = Modifier
                    .height(320.dp)
                    .padding(horizontal = 24.dp)
            ) {
                Image(
                    painter = painterResource(id = movie.assetImage),
                    contentDescription = "Movie Image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .weight(0.7f)
                        .height(320.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                Spacer(modifier = Modifier.width(24.dp))
                Column(
                    modifier = Modifier
                        .height(320.dp)
                        .weight(0.3f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    MovieInfo(
                        painterResourceId = R.drawable.baseline_videocam,
                        title = "Genre",
                        value = movie.type
                    )
                    MovieInfo(
                        painterResourceId = R.drawable.baseline_access_time_filled,
                        title = "Duration",
                        value = movie.duration
                    )
                    MovieInfo(
                        painterResourceId = R.drawable.baseline_stars,
                        title = "Rating",
                        value = movie.rating
                    )
                }
            }
            Text(
                movie.title, style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(
                    horizontal = 24.dp, vertical = 16.dp
                )
            )
            Text(
                "Synopsis", style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(
                    horizontal = 24.dp
                )
            )
            Text(
                movie.synopsis, style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(
                    horizontal = 24.dp, vertical = 16.dp
                )
            )
            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

@Composable
fun MovieInfo(
    @DrawableRes painterResourceId: Int,
    title: String,
    value: String,
) {
    Column(
        modifier = Modifier
            .border(width = 1.dp, color = Gray, shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .clickable { }
            .padding(12.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(id = painterResourceId),
            contentDescription = title,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = title, style = MaterialTheme.typography.body2)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value, style = MaterialTheme.typography.subtitle1)
    }
}
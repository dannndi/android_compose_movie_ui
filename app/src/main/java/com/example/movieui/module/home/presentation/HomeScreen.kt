package com.example.movieui.module.home.presentation

import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.layout.lerp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.navigation.NavHostController
import com.example.movieui.R
import com.example.movieui.core.route.AppRouteName
import com.example.movieui.core.theme.BlueVariant
import com.example.movieui.core.theme.Gray
import com.example.movieui.core.theme.Yellow
import com.example.movieui.module.home.model.MovieModel
import com.example.movieui.module.home.model.nowPlayingMovie
import com.example.movieui.module.home.model.upcoming
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlin.math.absoluteValue

@Composable
fun HomeScreen(
    navController: NavHostController,
) {
    val scrollState = rememberScrollState()

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(
                    top = padding.calculateTopPadding() + 24.dp,
                    bottom = padding.calculateBottomPadding() + 24.dp,
                )
        ) {
            Text(
                text = "Welcome back, Dandi!",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Book your Favorite Movie Here!",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Banners()
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    text = "Category",
                    style = MaterialTheme.typography.h6,
                )
                TextButton(onClick = { }) {
                    Text(text = "See All")
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Categories()
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    text = "Now Playing Movie",
                    style = MaterialTheme.typography.h6,
                )
                TextButton(onClick = { }) {
                    Text(text = "See All")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            NowPlayingMovie { movie ->
                navController.navigate("${AppRouteName.Detail}/${movie.id}")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    text = "Upcoming Movie",
                    style = MaterialTheme.typography.h6,
                )
                TextButton(onClick = { }) {
                    Text(text = "See All")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            UpcomingMovie()
        }
    }
}

@Composable
fun UpcomingMovie() {
    LazyRow(
        contentPadding = PaddingValues(start = 24.dp)
    ) {
        items(count = upcoming.size) { index ->
            Box(modifier = Modifier
                .padding(end = 24.dp)
                .clickable { }) {
                Column(
                    modifier = Modifier.wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = upcoming[index].assetImage),
                        contentDescription = "Movie Image",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.size(width = 200.dp, height = 260.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = upcoming[index].title,
                        style = MaterialTheme.typography.subtitle1,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NowPlayingMovie(
    onMovieClicked: (MovieModel) -> Unit
) {
    HorizontalPager(
        count = nowPlayingMovie.size,
        contentPadding = PaddingValues(start = 48.dp, end = 48.dp)
    ) { page ->

        Column(
            modifier = Modifier
                .wrapContentHeight()
                .graphicsLayer {
                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                    lerp(
                        start = ScaleFactor(1f, 0.85f),
                        stop = ScaleFactor(1f, 1f),
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale.scaleX
                        scaleY = scale.scaleY
                    }
                }
                .clickable {
                    onMovieClicked(nowPlayingMovie[page])
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.clip(RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.BottomCenter

            ) {
                Image(
                    painter = painterResource(id = nowPlayingMovie[page].assetImage),
                    contentDescription = "Movie Image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.85f)
                        .height(340.dp)
                )
                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                            val translation = pageOffset.coerceIn(0f, 1f)

                            translationY = translation * 200
                        }
                        .fillMaxWidth(fraction = 0.85f)
                        .wrapContentHeight()
                        .background(
                            BlueVariant
                        )
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Buy Ticket", style = MaterialTheme.typography.subtitle1.copy(
                            color = Yellow,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = nowPlayingMovie[page].title,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun Categories() {
    val categories = listOf(
        "Animation",
        "Horror",
        "Action",
        "Comedy",
        "Romance",
        "Sci-fi",
        "History",
        "Adventure",
    )
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier.horizontalScroll(scrollState)
    ) {
        repeat(categories.size) { index ->
            Surface(
                /// order matters
                modifier = Modifier
                    .padding(
                        start = if (index == 0) 24.dp else 0.dp,
                        end = 12.dp,
                    )
                    .border(width = 1.dp, color = Gray, shape = RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { }
                    .padding(12.dp)
            ) {
                Text(text = categories[index], style = MaterialTheme.typography.caption)
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Banners() {
    val banners = listOf(
        R.drawable.banner_1,
        R.drawable.banner_2,
        R.drawable.banner_3,
    )

    val pagerState = rememberPagerState()
    val bannerIndex = remember { mutableStateOf(0) }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            bannerIndex.value = page
        }
    }

    /// auto scroll
    LaunchedEffect(Unit) {
        while (true) {
            delay(10_000)
            tween<Float>(1500)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % pagerState.pageCount
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
            .padding(horizontal = 24.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            count = banners.size,
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
        ) { index ->
            Image(
                painter = painterResource(id = banners[index]),
                contentDescription = "Banners",
                contentScale = ContentScale.FillBounds,
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            repeat(banners.size) { index ->
                val height = 12.dp
                val width = if (index == bannerIndex.value) 28.dp else 12.dp
                val color = if (index == bannerIndex.value) Yellow else Gray

                Surface(
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .size(width, height)
                        .clip(RoundedCornerShape(20.dp)),
                    color = color,
                ) {
                }
            }
        }
    }
}
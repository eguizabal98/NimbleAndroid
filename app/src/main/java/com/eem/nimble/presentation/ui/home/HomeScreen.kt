@file:OptIn(ExperimentalFoundationApi::class)

package com.eem.nimble.presentation.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.eem.domain.model.survey.SurveyData
import com.eem.nimble.R
import com.eem.nimble.presentation.componets.PagerIndicator
import com.eem.nimble.presentation.theme.NimbleAndroidTheme
import com.eem.nimble.presentation.ui.home.HomeViewModel.UIState

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {

    LaunchedEffect(key1 = true, block = {
        homeViewModel.getSurveyList()
    })
    HomeScreenContent(homeViewModel.uiState)
}

@Composable
fun HomeScreenContent(uiState: UIState = UIState()) {
    val surveyList: LazyPagingItems<SurveyData> = uiState.surveyList.collectAsLazyPagingItems()
    val state = rememberLazyListState()
    val currentIndex by remember {
        derivedStateOf {
            state.layoutInfo.visibleItemsInfo.firstOrNull()?.index ?: 0
        }
    }
    Box {
        when (surveyList.loadState.refresh) {
            LoadState.Loading -> {
            }

            is LoadState.Error -> {
            }

            else -> {
                LazyRow(
                    modifier = Modifier.fillMaxSize(),
                    state = state,
                    flingBehavior = rememberSnapFlingBehavior(lazyListState = state)
                ) {
                    items(
                        count = surveyList.itemCount
                    ) { item ->
                        surveyList[item]?.let { SurveyItem(Modifier.fillParentMaxSize(), it) }
                    }
                }
            }
        }
        Column(modifier = Modifier.fillMaxSize()) {
            UseAppBar()
        }
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
            PagerIndicator(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 150.dp),
                currentPage = currentIndex,
                pageCount = surveyList.itemCount
            )
        }
    }
}

@Composable
fun SurveyItem(modifier: Modifier, surveyData: SurveyData) {
    Box(modifier = modifier) {
        val brush = Brush.verticalGradient(
            listOf(
                Color(0x1A000000),
                Color(0xB3000000)
            )
        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(surveyData.attributes?.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Survey Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .blur(1.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush)
        )
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 40.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .weight(7f, true)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = surveyData.attributes?.title.orEmpty(),
                        style = TextStyle(
                            fontSize = 28.sp,
                            lineHeight = 34.sp,
                            fontFamily = FontFamily(Font(R.font.neuzeit)),
                            fontWeight = FontWeight(800),
                            color = Color.White
                        )
                    )
                    Text(
                        modifier = Modifier.padding(top = 20.dp),
                        text = surveyData.attributes?.description.orEmpty(),
                        style = TextStyle(
                            fontSize = 17.sp,
                            lineHeight = 22.sp,
                            fontFamily = FontFamily(Font(R.font.neuzeit)),
                            fontWeight = FontWeight(400),
                            color = Color(0x80FFFFFF),
                        )
                    )
                }
                Image(
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .size(70.dp)
                        .weight(3f, false)
                        .clip(CircleShape)
                        .background(color = Color(0xFFFFFFFF)),
                    painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                    contentDescription = "image description",
                    contentScale = ContentScale.None,
                )
            }
        }
    }
}

@Composable
fun UseAppBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Monday, June 15",
                style = TextStyle(
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.neuzeit)),
                    fontWeight = FontWeight(800),
                    color = Color.White
                )
            )
            Text(
                text = "Today",
                style = TextStyle(
                    fontSize = 34.sp,
                    lineHeight = 41.sp,
                    fontFamily = FontFamily(Font(R.font.neuzeit)),
                    fontWeight = FontWeight(800),
                    color = Color.White
                )
            )
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {
            Image(
                modifier = Modifier.size(36.dp),
                painter = painterResource(id = R.drawable.baseline_account_circle_24),
                contentDescription = "Background Nimble",
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenContentPreview() {
    NimbleAndroidTheme {
        HomeScreenContent()
    }
}
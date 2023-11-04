@file:OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)

package com.eem.nimble.presentation.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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
import com.eem.nimble.presentation.theme.robotoFamily
import com.eem.nimble.presentation.ui.home.HomeViewModel.UIState
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navigateToSurvey: (String) -> Unit,
    logOutNavigation: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = true, block = {
        homeViewModel.getSurveyList()
    })

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color(0xFF1E1E1E),
                modifier = Modifier.requiredWidth(250.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 25.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Mai",
                        modifier = Modifier.padding(16.dp),
                        style = TextStyle(
                            fontSize = 34.sp,
                            lineHeight = 41.sp,
                            fontFamily = robotoFamily,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    )
                    Image(
                        modifier = Modifier
                            .size(40.dp)
                            .padding(5.dp),
                        painter = painterResource(id = R.drawable.oval),
                        contentDescription = stringResource(R.string.background_nimble),
                        contentScale = ContentScale.Crop
                    )
                }
                Divider()

                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            homeViewModel.logOut {
                                logOutNavigation()
                            }
                        },
                    text = "Log Out",
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 25.sp,
                        fontFamily = robotoFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xBFFFFFFF),
                        letterSpacing = 0.38.sp
                    )
                )
            }
        }
    ) {
        HomeScreenContent(
            homeViewModel.uiState,
            { homeViewModel.stopLoading() },
            navigateToSurvey,
            homeViewModel.isRefreshing,
            {
                scope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreenContent(
    uiState: UIState = UIState(),
    stopLoading: () -> Unit = {},
    navigateToSurvey: (String) -> Unit = {},
    refreshing: StateFlow<Boolean> = MutableStateFlow(false),
    openDrawer: () -> Unit = {}
) {
    val refreshing by refreshing.collectAsState()

    val surveyList: LazyPagingItems<SurveyData> = uiState.surveyList.collectAsLazyPagingItems()
    val state = rememberLazyListState()
    val currentIndex by remember {
        derivedStateOf {
            state.layoutInfo.visibleItemsInfo.firstOrNull()?.index ?: 0
        }
    }
    var isLoading by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(refreshing, { surveyList.refresh() })

    LazyColumn(modifier = Modifier.pullRefresh(pullRefreshState), content = {
        item {
            Box(
                Modifier
                    .fillParentMaxSize()
            ) {
                when (surveyList.loadState.refresh) {
                    LoadState.Loading -> {
                        isLoading = true
                    }

                    is LoadState.Error -> {
                    }

                    else -> {
                        isLoading = false
                        stopLoading()
                        LazyRow(
                            modifier = Modifier.fillMaxSize(),
                            state = state,
                            flingBehavior = rememberSnapFlingBehavior(lazyListState = state)
                        ) {
                            items(
                                count = surveyList.itemCount
                            ) { item ->
                                surveyList[item]?.let {
                                    SurveyItem(
                                        Modifier.fillParentMaxSize(),
                                        it,
                                        navigateToSurvey
                                    )
                                }
                            }
                        }
                    }
                }
                if (isLoading.not()) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        UseAppBar(openDrawer)
                    }
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        PagerIndicator(
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 150.dp),
                            currentPage = currentIndex,
                            pageCount = surveyList.itemCount
                        )
                    }
                } else {
                    LoadingView()
                }
                PullRefreshIndicator(
                    isLoading,
                    pullRefreshState,
                    Modifier.align(Alignment.TopCenter)
                )
            }
        }
    })
}

@Composable
fun SurveyItem(modifier: Modifier, surveyData: SurveyData, navigateToSurvey: (String) -> Unit) {
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
            contentDescription = stringResource(R.string.survey_background),
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
                    .padding(horizontal = 20.dp, vertical = 50.dp)
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
                            fontFamily = robotoFamily,
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
                            fontFamily = robotoFamily,
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
                        .background(color = Color(0xFFFFFFFF))
                        .clickable {
                            navigateToSurvey(surveyData.id)
                        },
                    painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                    contentDescription = stringResource(R.string.see_survey),
                    contentScale = ContentScale.None
                )
            }
        }
    }
}

@Composable
fun UseAppBar(openDrawer: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 45.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(R.string.monday_june_15),
                style = TextStyle(
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight(800),
                    color = Color.White
                )
            )
            Text(
                text = stringResource(R.string.today),
                style = TextStyle(
                    fontSize = 34.sp,
                    lineHeight = 41.sp,
                    fontFamily = robotoFamily,
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
                modifier = Modifier
                    .size(36.dp)
                    .clickable { openDrawer() },
                painter = painterResource(id = R.drawable.oval),
                contentDescription = stringResource(R.string.background_nimble),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun LoadingView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(top = 45.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .width(175.dp)
                        .height(25.dp)
                        .shimmer()
                        .background(Color.White, CircleShape),
                    text = ""
                )
                Text(
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .width(100.dp)
                        .height(25.dp)
                        .shimmer()
                        .background(Color.White, CircleShape),
                    text = ""
                )
            }
            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                Text(
                    modifier = Modifier
                        .size(36.dp)
                        .shimmer()
                        .background(Color.White, CircleShape),
                    text = ""
                )
            }
        }
        Column(
            Modifier
                .weight(1f)
                .padding(bottom = 50.dp, start = 20.dp, end = 20.dp), verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .width(50.dp)
                    .height(25.dp)
                    .shimmer()
                    .background(Color.White, CircleShape),
                text = ""
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .width(150.dp)
                    .height(25.dp)
                    .shimmer()
                    .background(Color.White, CircleShape),
                text = ""
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .width(75.dp)
                    .height(25.dp)
                    .shimmer()
                    .background(Color.White, CircleShape),
                text = ""
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .width(300.dp)
                    .height(25.dp)
                    .shimmer()
                    .background(Color.White, CircleShape),
                text = ""
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .width(100.dp)
                    .height(25.dp)
                    .shimmer()
                    .background(Color.White, CircleShape),
                text = ""
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
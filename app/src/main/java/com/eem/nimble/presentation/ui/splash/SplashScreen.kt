package com.eem.nimble.presentation.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eem.nimble.R
import com.eem.nimble.presentation.theme.NimbleAndroidTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navigateLogin: () -> Unit = {},
    navigateToHome: () -> Unit = {},
    splashViewModel: SplashViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true, block = {
        delay(250)
        splashViewModel.isUserLogin()
    })
    LaunchedEffect(key1 = true, block = {
        splashViewModel.baseEvent.collect{event ->
            when(event) {
                is SplashViewModel.BaseEvent.OnNavigateToHome -> {
                    navigateToHome()
                }
                is SplashViewModel.BaseEvent.OnNavigateToLogin -> {
                    navigateLogin()
                }
            }
        }
    })
    Box {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.splash_background),
            contentDescription = "Background Nimble",
            contentScale = ContentScale.Crop
        )
        Column(
            Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.width(160.dp),
                painter = painterResource(id = R.drawable.logo_white),
                contentDescription = "Logo Nimble",
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NimbleAndroidTheme {
        SplashScreen()
    }
}

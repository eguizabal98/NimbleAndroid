package com.eem.nimble.presentation.componets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eem.nimble.R
import com.eem.nimble.presentation.theme.NimbleAndroidTheme

@Composable
fun BackgroundFrame(
    content: @Composable () -> Unit
) {
    Box {
        val brush = Brush.verticalGradient(
            listOf(
                Color(0x33000000),
                Color(0xFF000000)
            )
        )
        Image(
            modifier = Modifier
                .fillMaxSize()
                .blur(50.dp),
            painter = painterResource(id = R.drawable.splash_background),
            contentDescription = "Background Nimble",
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush)
        )
        content()
    }
}

@Preview
@Composable
fun BackgroundFramePreview() {
    NimbleAndroidTheme {
        BackgroundFrame {}
    }
}

package com.eem.nimble.presentation.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eem.nimble.R
import com.eem.nimble.presentation.componets.BackgroundFrame
import com.eem.nimble.presentation.componets.ButtonApp
import com.eem.nimble.presentation.componets.TextFieldApp
import com.eem.nimble.presentation.theme.NimbleAndroidTheme

@Composable
fun LoginScreen() {
    LoginScreenContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenContent() {
    BackgroundFrame {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.padding(vertical = 100.dp),
                painter = painterResource(id = R.drawable.logo_white),
                contentDescription = "Background Nimble",
                contentScale = ContentScale.Crop
            )
            TextFieldApp(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 24.dp)
                    .fillMaxWidth(),
                value = "",
                placeholder = "Email",
                onTextChange = {}
            )
            TextFieldApp(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 24.dp)
                    .fillMaxWidth(),
                value = "Hola",
                placeholder = "Password",
                isPassword = true,
                onTextChange = {},
                endComposable = {
                    Text(
                        text = "Forgot?",
                        style = TextStyle(
                            fontSize = 17.sp,
                            lineHeight = 22.sp,
                            fontFamily = FontFamily(Font(R.font.neuzeit)),
                            fontWeight = FontWeight.Normal,
                            color = Color(0x80FFFFFF),
                        )
                    )
                }
            )

            ButtonApp(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                onClick = { /*TODO*/ }, "Log In"
            )
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    NimbleAndroidTheme {
        LoginScreenContent()
    }
}

package com.eem.nimble.presentation.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.eem.nimble.R
import com.eem.nimble.presentation.componets.BackgroundFrame
import com.eem.nimble.presentation.componets.ButtonApp
import com.eem.nimble.presentation.componets.LoadingIndicator
import com.eem.nimble.presentation.componets.TextFieldApp
import com.eem.nimble.presentation.theme.NimbleAndroidTheme
import com.eem.nimble.presentation.ui.login.LoginViewModel.LoginUIEventActions
import com.eem.nimble.presentation.ui.login.LoginViewModel.UIEvent.OnLoginClick
import com.eem.nimble.presentation.ui.login.LoginViewModel.UIEvent.OnUserEmailChange
import com.eem.nimble.presentation.ui.login.LoginViewModel.UIEvent.OnUserPasswordChange
import com.eem.nimble.presentation.ui.login.LoginViewModel.UIState

@Composable
fun LoginScreen(
    navigateToHome: () -> Unit,
    navigateToSignUp: () -> Unit,
    navigateToReset: () -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true, block = {
        loginViewModel.baseEvent.collect { event ->
            when (event) {
                is LoginViewModel.BaseEvent.OnNavigateToHome -> {
                    navigateToHome()
                }
            }
        }
    })
    LoginScreenContent(
        loginViewModel.uiState,
        LoginUIEventActions(
            signUpClick = { navigateToSignUp() },
            loginClick = { loginViewModel.onUIEvent(OnLoginClick) },
            userEmailChange = { loginViewModel.onUIEvent(OnUserEmailChange(it)) },
            userPasswordChange = { loginViewModel.onUIEvent(OnUserPasswordChange(it)) },
            forgotPasswordClick = { navigateToReset() }
        )
    )
}

@Composable
fun LoginScreenContent(
    uiState: UIState = UIState(),
    loginUIEventActions: LoginUIEventActions = LoginUIEventActions()
) {
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
                value = uiState.email,
                placeholder = "Email",
                onTextChange = { loginUIEventActions.userEmailChange(it) }
            )
            TextFieldApp(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 24.dp)
                    .fillMaxWidth(),
                value = uiState.password,
                placeholder = "Password",
                isPassword = true,
                onTextChange = { loginUIEventActions.userPasswordChange(it) },
                endComposable = {
                    Text(
                        text = "Forgot?",
                        style = TextStyle(
                            fontSize = 17.sp,
                            lineHeight = 22.sp,
                            fontFamily = FontFamily(Font(R.font.neuzeit)),
                            fontWeight = FontWeight.Normal,
                            color = Color(0x80FFFFFF),
                        ),
                        modifier = Modifier.clickable {
                            loginUIEventActions.forgotPasswordClick()
                        }
                    )
                }
            )

            Text(
                text = uiState.loginError,
                style = TextStyle(
                    fontSize = 17.sp,
                    lineHeight = 22.sp,
                    fontFamily = FontFamily(Font(R.font.neuzeit)),
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFFFFFFFF),
                )
            )

            ButtonApp(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                onClick = { loginUIEventActions.loginClick() }, "Log In"
            )
        }
        LoadingIndicator(uiState.loading)
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    NimbleAndroidTheme {
        LoginScreenContent()
    }
}

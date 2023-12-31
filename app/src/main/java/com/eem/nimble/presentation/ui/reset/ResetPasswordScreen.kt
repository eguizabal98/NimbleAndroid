package com.eem.nimble.presentation.ui.reset

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
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
import com.eem.nimble.presentation.theme.robotoFamily
import com.eem.nimble.presentation.ui.reset.ResetPasswordViewModel.ResetUIEventActions
import com.eem.nimble.presentation.ui.reset.ResetPasswordViewModel.UIEvent.OnClearMessage
import com.eem.nimble.presentation.ui.reset.ResetPasswordViewModel.UIEvent.OnResetClick
import com.eem.nimble.presentation.ui.reset.ResetPasswordViewModel.UIEvent.OnUserEmailChange
import com.eem.nimble.presentation.ui.reset.ResetPasswordViewModel.UIState

@Composable
fun ResetPasswordScreen(
    resetPasswordViewModel: ResetPasswordViewModel = hiltViewModel(),
    navigateBack: () -> Unit = {}
) {

    ResetPasswordContent(
        resetPasswordViewModel.uiState,
        ResetUIEventActions(
            resetClick = { resetPasswordViewModel.onUIEvent(OnResetClick) },
            userEmailChange = { resetPasswordViewModel.onUIEvent(OnUserEmailChange(it)) },
            clearMessage = { resetPasswordViewModel.onUIEvent(OnClearMessage) }
        ),
        navigateBack
    )
}

@Composable
fun ResetPasswordContent(
    uiState: UIState = UIState(),
    resetUIEventActions: ResetUIEventActions = ResetUIEventActions(),
    navigateBack: () -> Unit = {}
) {

    BackgroundFrame {
        if (uiState.successMessage.isNotEmpty()) {
            AlertDialog(
                onDismissRequest = { resetUIEventActions.clearMessage() },
                confirmButton = {
                    Text(
                        text = "OK",
                        modifier = Modifier.clickable { resetUIEventActions.clearMessage() })
                },
                text = { Text(text = uiState.successMessage) })
        }
        Column(
            modifier = Modifier.padding(
                top = 45.dp,
                bottom = 24.dp,
                start = 24.dp,
                end = 24.dp
            )
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow),
                    contentDescription = stringResource(R.string.background_nimble),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clickable {
                        navigateBack()
                    }
                )
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.padding(top = 50.dp, bottom = 24.dp),
                    painter = painterResource(id = R.drawable.logo_white),
                    contentDescription = stringResource(R.string.nimble_logo),
                    contentScale = ContentScale.Crop
                )
                Text(
                    modifier = Modifier.padding(top = 24.dp, bottom = 90.dp),
                    text = stringResource(R.string.enter_your_email),
                    style = TextStyle(
                        fontSize = 17.sp,
                        lineHeight = 22.sp,
                        fontFamily = robotoFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFFFFFFFF),

                        textAlign = TextAlign.Center,
                    )
                )
                TextFieldApp(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth(),
                    value = uiState.email,
                    placeholder = stringResource(R.string.email),
                    onTextChange = { resetUIEventActions.userEmailChange(it) },
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Email
                )

                ButtonApp(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    onClick = { resetUIEventActions.resetClick() }, stringResource(R.string.reset)
                )
            }
        }
        LoadingIndicator(uiState.loading)
    }
}

@Preview
@Composable
fun ResetPasswordContentPreview() {
    NimbleAndroidTheme {
        ResetPasswordContent()
    }
}
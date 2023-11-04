package com.eem.nimble.presentation.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.eem.nimble.presentation.theme.NimbleAndroidTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private var uiState by mutableStateOf(LoginViewModel.UIState())
    private lateinit var loginUIEventActions: LoginViewModel.LoginUIEventActions

    @Before
    fun setup() {
        uiState = LoginViewModel.UIState()
        loginUIEventActions = LoginViewModel.LoginUIEventActions()

        composeTestRule.setContent {
            NimbleAndroidTheme {
                LoginScreenContent(uiState, loginUIEventActions)
            }
        }
    }

    @Test
    fun testLoginScreenContent() {
        uiState = uiState.copy(email = "test@example.com")
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("test@example.com", useUnmergedTree = true).assertExists()

        uiState = uiState.copy(password = "password123")
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("•••••••••••", useUnmergedTree = true).assertExists()
    }

    @Test
    fun testLoginError() {
        uiState = uiState.copy(loginError = "Error: Credenciales incorrectas")
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Error: Credenciales incorrectas").assertExists()
    }
}

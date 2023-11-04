package com.eem.nimble.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eem.nimble.presentation.ui.SurveyScreen
import com.eem.nimble.presentation.ui.home.HomeScreen
import com.eem.nimble.presentation.ui.login.LoginScreen
import com.eem.nimble.presentation.ui.splash.SplashScreen

@Composable
fun NavApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(
                navigateLogin = {
                    navController.navigate("login")
                },
                navigateToHome = {
                    navController.navigate("home")
                }
            )
        }
        composable("login") {
            LoginScreen(
                navigateToHome = { navController.navigate("home") },
                navigateToSignUp = { navController.navigate("signUp") }
            )
        }
        composable("home") {
            HomeScreen(
                navigateToSurvey = {
                    navController.navigate("survey")
                }
            )
        }
        composable("survey") {
            SurveyScreen()
        }
    }
}
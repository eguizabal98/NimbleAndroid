package com.eem.nimble.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eem.nimble.presentation.ui.SurveyScreen
import com.eem.nimble.presentation.ui.home.HomeScreen
import com.eem.nimble.presentation.ui.login.LoginScreen
import com.eem.nimble.presentation.ui.reset.ResetPasswordScreen
import com.eem.nimble.presentation.ui.splash.SplashScreen

@Composable
fun NavApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(
                navigateLogin = {
                    navController.navigate("login") {
                        popUpTo("splash") { inclusive = true }
                    }
                },
                navigateToHome = {
                    navController.navigate("home") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }
        composable("login") {
            LoginScreen(
                navigateToHome = {
                    navController.navigate("home") {
                        popUpTo("splash") { inclusive = true }
                    }
                },
                navigateToSignUp = { navController.navigate("signUp") },
                navigateToReset = { navController.navigate("reset") }
            )
        }
        composable("reset") {
            ResetPasswordScreen(navigateBack = {
                navController.popBackStack()
            })
        }
        composable("home") {
            HomeScreen(
                navigateToSurvey = {
                    navController.navigate("survey")
                },
                logOutNavigation = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("survey") {
            SurveyScreen()
        }
    }
}
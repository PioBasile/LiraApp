package com.app.lira.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.lira.ui.screens.LoginScreen
import com.app.lira.ui.screens.AddPayedScreen

@Composable
fun LiraNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "addPayed"
    ) {
        composable("login") {
            LoginScreen(navController)
        }
        composable("addPayed") {
            AddPayedScreen(navController)
        }
    }
}
package com.roshanadke.inspireme.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.roshanadke.inspireme.presentation.screen.QuotesMainScreen

@Composable
fun Navigation() {


    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.QuoteMainScreen.route) {
        composable(Screen.QuoteMainScreen.route) {
            QuotesMainScreen(navController = navController)
        }
    }



}
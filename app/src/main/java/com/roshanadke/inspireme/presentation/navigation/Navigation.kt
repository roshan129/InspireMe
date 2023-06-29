package com.roshanadke.inspireme.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.roshanadke.inspireme.presentation.screen.AuthorDetailsScreen
import com.roshanadke.inspireme.presentation.screen.QuotesMainScreen

@Composable
fun Navigation() {


    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.QuoteMainScreen.route) {
        composable(Screen.QuoteMainScreen.route) {
            //QuotesMainScreen(navController = navController)
            AuthorDetailsScreen(author = "ovid")
        }
        composable(
            Screen.AuthorDetailsScreen.route + "/{author}",
            arguments = listOf(
                navArgument("author") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { navBackStackEntry ->
            val author = navBackStackEntry.arguments?.getString("author")
            AuthorDetailsScreen(author = author)

        }
    }


}
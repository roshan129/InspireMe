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
            QuotesMainScreen(navController = navController)
            //AuthorDetailsScreen(author = "ovid")
        }
        composable(
            Screen.AuthorDetailsScreen.route + "/{authorSlug}/{authorName}",
            arguments = listOf(
                navArgument("authorSlug") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("authorName") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { navBackStackEntry ->
            val authorSlug = navBackStackEntry.arguments?.getString("authorSlug")
            val authorName = navBackStackEntry.arguments?.getString("authorName")
            AuthorDetailsScreen(
                navController = navController,
                authorSlug = authorSlug,
                authorName = authorName
            )

        }
    }


}
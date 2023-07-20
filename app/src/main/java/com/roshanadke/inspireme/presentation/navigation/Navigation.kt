package com.roshanadke.inspireme.presentation.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.roshanadke.inspireme.domain.model.Quote
import com.roshanadke.inspireme.presentation.screen.AuthorDetailsScreen
import com.roshanadke.inspireme.presentation.screen.QuotesDownloadScreen
import com.roshanadke.inspireme.presentation.screen.QuotesMainScreen

@Composable
fun Navigation() {


    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = Screen.QuoteMainScreen.route,
    ) {
        composable(Screen.QuoteMainScreen.route,
            /*enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left,animationSpec = tween(700))
            }*/
        ) {
            QuotesMainScreen(navController = navController)
            //val quoteObj = Quote("Abcd Temp", "", "sdfsdf dsfs  dsf sdf dsf sdf sdf sdf ", 0, emptyList())
            //QuotesDownloadScreen(quote = quoteObj)
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
            ),
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
            }

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
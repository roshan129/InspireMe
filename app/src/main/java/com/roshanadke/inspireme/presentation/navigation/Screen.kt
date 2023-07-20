package com.roshanadke.inspireme.presentation.navigation

sealed class Screen(val route: String) {

    object QuoteMainScreen : Screen("QuotesMainScreen")
    object AuthorDetailsScreen : Screen("AuthorDetailsScreen")

    object QuotesDownloadScreen : Screen("QuotesDownloadScreen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}

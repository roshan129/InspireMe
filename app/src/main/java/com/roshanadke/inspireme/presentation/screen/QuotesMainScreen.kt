package com.roshanadke.inspireme.presentation.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.roshanadke.inspireme.presentation.viewmodel.QuotesViewModel


@Composable
fun QuotesMainScreen(
    viewModel: QuotesViewModel = hiltViewModel(),
    navController: NavController
) {


    Text(text = "On Quotes screen")


}
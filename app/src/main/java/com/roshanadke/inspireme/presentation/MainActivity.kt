package com.roshanadke.inspireme.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roshanadke.inspireme.presentation.navigation.Navigation
import com.roshanadke.inspireme.presentation.ui.theme.BackGroundColor
import com.roshanadke.inspireme.presentation.ui.theme.InspireMeTheme
import com.roshanadke.inspireme.presentation.viewmodel.QuotesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InspireMeTheme {

                /*val quotesViewModel: QuotesViewModel =  hiltViewModel()
                val quote = quotesViewModel.singleQuote.value
                val randomQuotes = quotesViewModel.randomQuotes.value*/

                Surface(
                    contentColor = BackGroundColor
                ) {
                    Navigation()
                }

            }
        }
    }
}


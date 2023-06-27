package com.roshanadke.inspireme.presentation.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.roshanadke.inspireme.R
import com.roshanadke.inspireme.presentation.ui.theme.BackGroundColor
import com.roshanadke.inspireme.presentation.ui.theme.QuoteTextColor
import com.roshanadke.inspireme.presentation.viewmodel.QuotesViewModel


@Composable
fun QuotesMainScreen(
    viewModel: QuotesViewModel = hiltViewModel(),
    navController: NavController
) {


    //Text(text = "On Quotes screen")

    val quotes = viewModel.randomQuotes.value


    quotes.forEach {
        Log.d("TAG", "QuotesMainScreen: ${it.author}")
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        LazyColumn(
            modifier = Modifier.background(
                color = BackGroundColor
            )
        ) {

            items(quotes) { quote ->

                Column(
                    modifier = Modifier
                        .fillParentMaxHeight()
                        .padding(16.dp, top = 24.dp),
                    verticalArrangement = Arrangement.Center,
                ) {

                    Column(
                        modifier = Modifier.weight(2f),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = quote.content,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
                            color = QuoteTextColor,

                            )

                        Text(
                            text = "- ${quote.author}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 24.dp, top = 12.dp),
                            textAlign = TextAlign.End,
                            color = QuoteTextColor,
                        )
                    }

                    Box(
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .fillMaxWidth()
                            .weight(1f),
                        /*.border(color = Color.White, width = 2.dp),*/
                        contentAlignment = Alignment.Center
                    ) {

                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.fillMaxWidth().padding(24.dp)
                        ) {

                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )

                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Share",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )

                            Icon(
                                imageVector = Icons.Default.Download,
                                contentDescription = "Share",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )

                            Icon(
                                imageVector = Icons.Default.CopyAll,
                                contentDescription = "Share",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                    }


                }


            }
        }

    }


}
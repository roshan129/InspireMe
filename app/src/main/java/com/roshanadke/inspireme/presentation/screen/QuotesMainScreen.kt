package com.roshanadke.inspireme.presentation.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.NoAccounts
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.roshanadke.inspireme.presentation.navigation.Screen
import com.roshanadke.inspireme.presentation.ui.theme.BackGroundColor
import com.roshanadke.inspireme.presentation.ui.theme.QuoteTextColor
import com.roshanadke.inspireme.presentation.ui.theme.SlateGray
import com.roshanadke.inspireme.presentation.viewmodel.QuotesViewModel


@OptIn(ExperimentalMaterial3Api::class)
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
                        .padding(start = 0.dp, top = 0.dp, end = 0.dp),
                    verticalArrangement = Arrangement.Center,
                ) {

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(12.dp),
                        /*.border(color = Color.White, width = 2.dp),*/
                        verticalArrangement = Arrangement.Center,

                        ) {
                        Spacer(modifier = Modifier.fillMaxWidth().height(100.dp))
                        Text(
                            text = quote.content,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.fillMaxWidth(),
                            color = QuoteTextColor,
                            textAlign = TextAlign.Center

                            )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 48.dp),
                            contentAlignment = Alignment.CenterEnd


                        ) {
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                colors = CardDefaults.cardColors(containerColor = SlateGray),
                                onClick = {
                                    navController.navigate(
                                        Screen.AuthorDetailsScreen.withArgs(quote.authorSlug)
                                    )
                                }
                            ) {
                                Text(
                                    text = "- ${quote.author}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal,
                                    modifier = Modifier
                                        .padding(
                                            start = 24.dp,
                                            end = 24.dp,
                                            top = 18.dp,
                                            bottom = 18.dp
                                        ),
                                    textAlign = TextAlign.End,
                                    color = QuoteTextColor,
                                )
                            }
                        }


                    }

                    Box(
                        modifier = Modifier
                            .padding(top = 12.dp, bottom = 24.dp)
                            .fillMaxWidth(),
                        /*.weight(0.4f),*/
                        /*.border(color = Color.White, width = 2.dp),*/
                        contentAlignment = Alignment.BottomCenter
                    ) {

                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp)
                        ) {

                            val iconSize = 48.dp

                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share",
                                tint = Color.White,
                                modifier = Modifier.size(iconSize)
                            )

                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Share",
                                tint = Color.White,
                                modifier = Modifier.size(iconSize)
                            )

                            Icon(
                                imageVector = Icons.Default.Download,
                                contentDescription = "Share",
                                tint = Color.White,
                                modifier = Modifier.size(iconSize)
                            )

                            Icon(
                                imageVector = Icons.Default.CopyAll,
                                contentDescription = "Share",
                                tint = Color.White,
                                modifier = Modifier.size(iconSize)
                            )
                        }

                    }


                }


            }
        }




        Box(
            modifier = Modifier
                .padding(top = 0.dp)
                .fillMaxSize(),
            /*.border(color = Color.White, width = 2.dp),*/
            contentAlignment = Alignment.TopStart
        ) {

            Card(
                modifier = Modifier
                    .padding(24.dp)
                    .size(60.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = SlateGray),
            ) {

                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Share",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(12.dp)
                        .size(42.dp)

                )


            }

        }


    }


}
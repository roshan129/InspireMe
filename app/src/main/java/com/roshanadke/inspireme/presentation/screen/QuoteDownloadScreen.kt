package com.roshanadke.inspireme.presentation.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roshanadke.inspireme.domain.model.Quote
import com.roshanadke.inspireme.presentation.navigation.Screen
import com.roshanadke.inspireme.presentation.ui.theme.BackGroundColor
import com.roshanadke.inspireme.presentation.ui.theme.QuoteTextColor
import com.roshanadke.inspireme.presentation.ui.theme.SlateGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuotesDownloadScreen(quote: Quote) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 0.dp, top = 0.dp, end = 0.dp)
                .background(color = BackGroundColor),
            verticalArrangement = Arrangement.Center,
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp),
                /*.border(color = Color.White, width = 2.dp),*/
                verticalArrangement = Arrangement.Center,

                ) {
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp))
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
                            /*quotesViewModel.changeSelectedAuthorName(quote.author)
                            navController.navigate(
                                Screen.AuthorDetailsScreen.withArgs(quote.authorSlug, quote.author)
                            ) {
                                *//*popUpTo(Screen.QuoteMainScreen.route) { inclusive = true }*//*
                                    }*/
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

        }


    }


}
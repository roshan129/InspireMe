package com.roshanadke.inspireme.presentation.components

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roshanadke.inspireme.common.MultipleEventsCutter
import com.roshanadke.inspireme.common.get
import com.roshanadke.inspireme.domain.model.Quote
import com.roshanadke.inspireme.presentation.ui.theme.BackGroundColor
import com.roshanadke.inspireme.presentation.ui.theme.QuoteTextColor
import com.roshanadke.inspireme.presentation.ui.theme.SlateGray
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun QuotesListScreen(
    modifier: Modifier,
    quotes: List<Quote>,
    onAuthorTabClicked: (quote: Quote) -> Unit,
    shareButtonClicked: (quote: Quote) -> Unit,
    downloadButtonClicked: (quote: Quote) -> Unit,
    copyButtonClicked: (quote: Quote) -> Unit,
    loadMoreQuotes: () -> Unit,
    resetList: SharedFlow<Boolean>
) {

    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        resetList.collectLatest {
            listState.animateScrollToItem(0)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackGroundColor)

    ) {
        LazyColumn(
            modifier = Modifier.background(
                color = BackGroundColor
            ),
            state = listState,
            flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

        ) {

            itemsIndexed(quotes) { index, quote ->

                if (index == quotes.size - 5) {
                    loadMoreQuotes()
                }

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
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                        )
                        Text(
                            text = quote.content,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.fillMaxWidth(),
                            color = QuoteTextColor,
                            textAlign = TextAlign.Center,
                            lineHeight = 36.sp
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 48.dp),
                            contentAlignment = Alignment.CenterEnd


                        ) {
                            val multipleEventsCutter = remember { MultipleEventsCutter.get() }

                            Card(
                                shape = RoundedCornerShape(16.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                colors = CardDefaults.cardColors(containerColor = SlateGray),
                                onClick = {
                                    multipleEventsCutter.processEvent {
                                        onAuthorTabClicked(quote)
                                    }
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
                                modifier = Modifier
                                    .size(iconSize)
                                    .clickable {
                                        shareButtonClicked(quote)

                                    }
                            )

                            /*     Icon(
                                     imageVector = Icons.Default.Favorite,
                                     contentDescription = "Share",
                                     tint = Color.White,
                                     modifier = Modifier.size(iconSize).
                                             clickable {
                                                 navController.navigate(
                                                     Screen.QuotesDownloadScreen.withArgs(quote.content)
                                                 )
                                             }
                                     ,

                                 )*/

                            Icon(
                                imageVector = Icons.Default.Download,
                                contentDescription = "Download",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(iconSize)
                                    .padding()
                                    .clickable {
                                        downloadButtonClicked(quote)

                                    }
                            )

                            Icon(
                                imageVector = Icons.Default.CopyAll,
                                contentDescription = "Copy",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(iconSize)
                                    .clickable {
                                        //copy quote to clipboard
                                        copyButtonClicked(quote)
                                    }
                            )
                        }

                    }


                }


            }
        }


    }
}
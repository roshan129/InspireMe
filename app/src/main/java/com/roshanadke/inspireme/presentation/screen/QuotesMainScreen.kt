package com.roshanadke.inspireme.presentation.screen

import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.roshanadke.inspireme.common.ComposableBitmapGenerator
import com.roshanadke.inspireme.common.saveBitmapAsImage
import com.roshanadke.inspireme.common.shareBitmap
import com.roshanadke.inspireme.common.showToast
import com.roshanadke.inspireme.domain.model.Quote
import com.roshanadke.inspireme.presentation.navigation.Screen
import com.roshanadke.inspireme.presentation.ui.theme.BackGroundColor
import com.roshanadke.inspireme.presentation.ui.theme.QuoteTextColor
import com.roshanadke.inspireme.presentation.ui.theme.SlateGray
import com.roshanadke.inspireme.presentation.viewmodel.QuotesViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuotesMainScreen(
    quotesViewModel: QuotesViewModel = hiltViewModel(),
    navController: NavController
) {

    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    //val quotes = quotesViewModel.randomQuotes.value

    val quotesListState = quotesViewModel.quotesListState.value

    var isInitialApiCallCompleted by rememberSaveable {
        mutableStateOf(false)
    }

    val bitmapState: MutableState<Bitmap?> = rememberSaveable {
        mutableStateOf(null)
    }

    var onDownloadImageClickFlag by rememberSaveable {
        mutableStateOf(false)
    }

    var isShareButtonClicked by rememberSaveable {
        mutableStateOf(false)
    }

    val downloadQuote: MutableState<Quote?> = rememberSaveable {
        mutableStateOf(null)
    }

    if (isShareButtonClicked) {
        AndroidView(
            factory = { ctxt ->
                val quoteView = ComposableBitmapGenerator(
                    ctx = ctxt,
                    quote = downloadQuote.value,
                )
                { bitmap ->
                    shareBitmap(context, bitmap, "Share Quote")
                }
                quoteView
            })
        isShareButtonClicked = false
    }

    if (!isInitialApiCallCompleted) {
        LaunchedEffect(Unit) {
            quotesViewModel.getRandomQuotes()
            isInitialApiCallCompleted = true
        }
    }

    if (onDownloadImageClickFlag) {
        //download Image
        onDownloadImageClickFlag = false
        AndroidView(
            factory = { ctxt ->
                val catView = ComposableBitmapGenerator(
                    ctx = ctxt,
                    quote = downloadQuote.value,
                )
                { bitmap ->
                    bitmapState.value = bitmap
                    val isSaved = saveBitmapAsImage(bitmap)
                    if (isSaved) {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "The quote was saved to the gallery",
                                withDismissAction = true
                            )
                        }
                    }
                }
                catView
            })

    }


    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
    ) {

        QuotesListScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            quotesListState.randomQuotesList,
            onAuthorTabClicked = { quote ->
                quotesViewModel.changeSelectedAuthorName(quote.author)
                navController.navigate(
                    Screen.AuthorDetailsScreen.withArgs(quote.authorSlug, quote.author)
                )
            },
            shareButtonClicked = { quote ->
                downloadQuote.value = quote
                isShareButtonClicked = true
            },
            downloadButtonClicked = { quote ->
                downloadQuote.value = quote
                onDownloadImageClickFlag = true
            },
            copyButtonClicked = { quote ->
                clipboardManager.setText(
                    AnnotatedString(quote.content)
                )
                context.showToast("Quote Copied")
            },
        )

        if(quotesListState.isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuotesListScreen(
    modifier: Modifier,
    quotes: List<Quote>,
    onAuthorTabClicked: (quote: Quote) -> Unit,
    shareButtonClicked: (quote: Quote) -> Unit,
    downloadButtonClicked: (quote: Quote) -> Unit,
    copyButtonClicked: (quote: Quote) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackGroundColor)
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
                                    Log.d("TAG", "QuotesMainScreen: author name: ${quote.author} ")
                                    onAuthorTabClicked(quote)

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

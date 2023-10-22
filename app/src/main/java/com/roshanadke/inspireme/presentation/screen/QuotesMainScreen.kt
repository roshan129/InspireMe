package com.roshanadke.inspireme.presentation.screen

import android.graphics.Bitmap
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.roshanadke.inspireme.R
import com.roshanadke.inspireme.common.ComposableBitmapGenerator
import com.roshanadke.inspireme.common.Constants
import com.roshanadke.inspireme.common.MultipleEventsCutter
import com.roshanadke.inspireme.common.get
import com.roshanadke.inspireme.common.saveBitmapAsImage
import com.roshanadke.inspireme.common.shareBitmap
import com.roshanadke.inspireme.common.showToast
import com.roshanadke.inspireme.domain.model.Quote
import com.roshanadke.inspireme.presentation.components.CategoryLayout
import com.roshanadke.inspireme.presentation.components.QuotesListScreen
import com.roshanadke.inspireme.presentation.navigation.Screen
import com.roshanadke.inspireme.presentation.ui.theme.BackGroundColor
import com.roshanadke.inspireme.presentation.ui.theme.QuoteTextColor
import com.roshanadke.inspireme.presentation.ui.theme.SlateGray
import com.roshanadke.inspireme.presentation.viewmodel.QuotesViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.LaunchedEffect as LaunchedEffect1


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuotesMainScreen(
    quotesViewModel: QuotesViewModel = hiltViewModel(),
    navController: NavController
) {

    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    //val quotes = quotesViewModel.randomQuotes.value

    val quotesListState = quotesViewModel.quotesListState.value
    val selectedCategory = quotesViewModel.quotesCategory

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
        LaunchedEffect1(Unit) {
            quotesViewModel.getQuotes()
            isInitialApiCallCompleted = true
        }
    }

    if (onDownloadImageClickFlag) {
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
        containerColor = BackGroundColor
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
            loadMoreQuotes = {
                quotesViewModel.loadMore()
            }
        )

        Box(
            modifier = Modifier
                .padding(top = 0.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.TopStart
        ) {

            Card(
                modifier = Modifier
                    .padding(24.dp)
                    .wrapContentWidth()
                    .clickable {
                        showBottomSheet = true
                    },
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = SlateGray),
            ) {
                val category =
                    if (selectedCategory.value.isEmpty()) "General" else selectedCategory.value

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val widthAround = 14.dp
                    Spacer(modifier = Modifier.width(widthAround))
                    Icon(
                        painter = painterResource(id = R.drawable.icon_baseline_apps),
                        contentDescription = "apps icon",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = category,
                        color = Color.White,
                        modifier = Modifier.padding(
                            top = widthAround,
                            bottom = widthAround,
                            end = widthAround
                        ),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                    )
                }

            }

        }


        if (quotesListState.isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxSize(),
                onDismissRequest = {
                    showBottomSheet = false
                },
                containerColor = BackGroundColor,
                sheetState = sheetState
            ) {

                CategoryLayout(
                    onCategoryCardClicked = { category ->
                        if (category.equals("General")) {
                            quotesViewModel.changeCategory("")
                        } else {
                            quotesViewModel.changeCategory(category)
                        }
                        showBottomSheet = false
                        quotesViewModel.getQuotes()
                    }
                )

            }
        }
    }

}




package com.roshanadke.inspireme.presentation.screen

import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.roshanadke.inspireme.R
import com.roshanadke.inspireme.common.ComposableBitmapGenerator
import com.roshanadke.inspireme.common.UiEvent
import com.roshanadke.inspireme.common.getSnackBarBackgroundColor
import com.roshanadke.inspireme.common.saveBitmapAsImage
import com.roshanadke.inspireme.common.shareBitmap
import com.roshanadke.inspireme.common.showToast
import com.roshanadke.inspireme.domain.connectivity.ConnectivityObserver
import com.roshanadke.inspireme.domain.model.Quote
import com.roshanadke.inspireme.presentation.components.CategoryLayout
import com.roshanadke.inspireme.presentation.components.QuotesListScreen
import com.roshanadke.inspireme.presentation.navigation.Screen
import com.roshanadke.inspireme.presentation.ui.theme.BackGroundColor
import com.roshanadke.inspireme.presentation.ui.theme.SlateGray
import com.roshanadke.inspireme.presentation.viewmodel.QuotesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


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

    val quotesListState = quotesViewModel.quotesListState.value
    val selectedCategory = quotesViewModel.quotesCategory
    val internetConnection = quotesViewModel.networkConnection.value

    var isFirstTimeLaunched by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(internetConnection) {
        if(isFirstTimeLaunched) {
            isFirstTimeLaunched = false
            return@LaunchedEffect
        }
        when(internetConnection) {
            ConnectivityObserver.Status.AVAILABLE -> {
                snackbarHostState.showSnackbar(context.getString(R.string.internet_connection_restored))
            }
            ConnectivityObserver.Status.UNAVAILABLE -> {
                snackbarHostState.showSnackbar(context.getString(R.string.no_internet_connection))
            }
            ConnectivityObserver.Status.LOST -> {
                snackbarHostState.showSnackbar(context.getString(R.string.no_internet_connection))
            }
            else -> {}
        }
    }

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
            quotesViewModel.getQuotes()
            isInitialApiCallCompleted = true

            quotesViewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is UiEvent.ShowSnackbar -> {
                        snackbarHostState.showSnackbar(event.message.asString(context))
                    }
                }
            }

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
            SnackbarHost(snackbarHostState) {
                Snackbar(
                    snackbarData = it,
                    containerColor = getSnackBarBackgroundColor(context, it.visuals.message)
                )
            }
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
            },
            resetList = quotesViewModel.shouldResetQuotesList
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

                        if (quotesViewModel.isCategoryChanged(category)) {
                            if (category.equals("General")) {
                                quotesViewModel.changeCategory("")
                            } else {
                                quotesViewModel.changeCategory(category)
                            }
                            showBottomSheet = false
                            quotesViewModel.getQuotes()
                        }

                    }
                )

            }
        }
    }

}




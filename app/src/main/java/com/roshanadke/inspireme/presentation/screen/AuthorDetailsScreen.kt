@file:OptIn(ExperimentalCoilApi::class)

package com.roshanadke.inspireme.presentation.screen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.roshanadke.inspireme.R
import com.roshanadke.inspireme.common.rememberWindowSize
import com.roshanadke.inspireme.domain.model.Quote
import com.roshanadke.inspireme.presentation.components.AuthorQuoteCard
import com.roshanadke.inspireme.presentation.ui.theme.BackGroundColor
import com.roshanadke.inspireme.presentation.ui.theme.LightRed
import com.roshanadke.inspireme.presentation.viewmodel.AuthorViewModel
import com.roshanadke.inspireme.presentation.viewmodel.QuotesViewModel.UiEvent
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalCoilApi::class, ExperimentalFoundationApi::class)
@Composable
fun AuthorDetailsScreen(
    navController: NavController,
    authorViewModel: AuthorViewModel = hiltViewModel(),
    authorSlug: String? = null,
    authorName: String? = null,
) {

    val authorInfoState = authorViewModel.authorDataState.collectAsState().value
    val authorQuotesState = authorViewModel.authorQuotesState.collectAsState().value

    val authorWikipediaInfo = authorViewModel.authorWikipediaInfo.value

    var isApiCalledOnce by rememberSaveable {
        mutableStateOf(false)
    }

    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val windowSizeInfo = rememberWindowSize()

    LaunchedEffect(Unit) {
        if (!isApiCalledOnce) {
            isApiCalledOnce = true
            authorSlug?.let {
                authorViewModel.getAuthorInfo(authorSlug)
                authorViewModel.getAuthorQuotes(authorSlug)
            }
            authorName?.let {
                authorViewModel.getAuthorWikipediaInfo(authorName)
            }
        }
        authorViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(event.message.asString(context))
                    //context.showToast(event.message.asString(context), Toast.LENGTH_SHORT)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHostState) {
                Snackbar(
                    containerColor = LightRed,
                    snackbarData = it
                )
            }
        }
    ) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(it)
                .background(
                    color = BackGroundColor
                )
        ) {

            stickyHeader {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(18.dp)
                        .size(48.dp)
                        .clickable {
                            navController.popBackStack()
                        },
                )
            }

            item {
                Column(
                    Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = BackGroundColor
                            ),
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                        ) {

                            if (windowSizeInfo.isCompactWidth()) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {

                                    //image url temp = "https://upload.wikimedia.org/wikipedia/commons/3/3e/Einstein_1921_by_F_Schmutzer_-_restoration.jpg"

                                    AuthorImage(
                                        imageSource = authorWikipediaInfo?.originalImage?.source,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp)
                                            .aspectRatio(1f)
                                            .clip(RoundedCornerShape(22.dp))
                                            .border(1.dp, Color.Gray)
                                    )

                                    Spacer(modifier = Modifier.height(30.dp))

                                    AuthorName(
                                        authorInfoState.authorInfo?.name,
                                        Modifier.fillMaxWidth()
                                    )

                                    Spacer(modifier = Modifier.height(20.dp))

                                    AuthorBio(
                                        authorBio = authorInfoState.authorInfo?.bio,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp)
                                    )
                                }

                            } else {
                                LandScapeAuthorDetails(
                                    imageSource = authorWikipediaInfo?.originalImage?.source,
                                    authorName = authorInfoState.authorInfo?.name,
                                    authorBio = authorInfoState.authorInfo?.bio
                                )
                            }


                        }

                    }

                }
            }

            item {
                AuthorTitleCard(authorName = authorName)
            }

            authorQuotesList(authorQuotesState.authorQuotes)

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        if (authorInfoState.isLoading) {
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

@Composable
fun LandScapeAuthorDetails(
    imageSource: String?,
    authorName: String?,
    authorBio: String?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
    ) {

        Box(modifier = Modifier.weight(0.8f)) {
            AuthorImage(
                imageSource = imageSource,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(22.dp))
                    .border(1.dp, Color.Gray)
            )
        }

        Box(modifier = Modifier.weight(1f)) {
            Column {
                Spacer(modifier = Modifier.height(30.dp))

                AuthorName(
                    authorName,
                    Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                AuthorBio(
                    authorBio = authorBio,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                )
            }
        }

    }
}

@Composable
fun AuthorImage(imageSource: String?, modifier: Modifier) {
    Image(
        painter = rememberImagePainter(
            data = imageSource,
            builder = {
                crossfade(true)
                placeholder(R.drawable.baseline_image_24)
            },
        ),
        contentDescription = "Author Profile",
        contentScale = ContentScale.Crop,
        modifier = modifier

    )
}

@Composable
fun AuthorName(name: String?, modifier: Modifier) {
    Text(
        text = name ?: "",
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = Color.White,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@Composable
fun AuthorBio(authorBio: String?, modifier: Modifier) {
    Text(
        text = authorBio ?: "",
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        color = Color.White,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

fun LazyListScope.authorQuotesList(authorQuotes: List<Quote>) {
    items(authorQuotes) { quote ->
        AuthorQuoteCard(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            quote = quote
        )
    }
}

@Composable
fun AuthorTitleCard(authorName: String?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = 4.dp
            ),
        colors = CardDefaults.cardColors(containerColor = BackGroundColor),
    ) {
        Text(
            text = "$authorName Quotes",
            color = Color.White,
            modifier = Modifier
                .padding(
                    24.dp
                ),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}
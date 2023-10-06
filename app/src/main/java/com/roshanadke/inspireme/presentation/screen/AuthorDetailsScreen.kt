package com.roshanadke.inspireme.presentation.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.roshanadke.inspireme.R
import com.roshanadke.inspireme.presentation.components.AuthorQuoteCard
import com.roshanadke.inspireme.presentation.ui.theme.BackGroundColor
import com.roshanadke.inspireme.presentation.ui.theme.SlateGray
import com.roshanadke.inspireme.presentation.viewmodel.QuotesViewModel


@OptIn(ExperimentalCoilApi::class, ExperimentalFoundationApi::class)
@Composable
fun AuthorDetailsScreen(
    navController: NavController,
    quotesViewModel: QuotesViewModel = hiltViewModel(),
    authorSlug: String? = null,
    authorName: String? = null,
) {

    val authorInfoState = quotesViewModel.authorDataState.collectAsState().value
    val authorQuotesState = quotesViewModel.authorQuotesState.collectAsState().value

    val authorWikipediaInfo = quotesViewModel.authorWikipediaInfo.value

    LaunchedEffect(Unit) {
        authorSlug?.let {
            quotesViewModel.getAuthorInfo(authorSlug)
            quotesViewModel.getAuthorQuotes(authorSlug)
        }
        authorName?.let {
            quotesViewModel.getAuthorWikipediaInfo(authorName)
        }
    }

    LazyColumn(
        Modifier
            .fillMaxSize()
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



                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            //image url temp = "https://upload.wikimedia.org/wikipedia/commons/3/3e/Einstein_1921_by_F_Schmutzer_-_restoration.jpg"

                            Image(
                                painter = rememberImagePainter(
                                    data = authorWikipediaInfo?.originalImage?.source,
                                    builder = {
                                        crossfade(true)
                                        placeholder(R.drawable.baseline_image_24)
                                    },
                                ),
                                contentDescription = "Author Profile",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    /*.height(350.dp)*/
                                    .padding(12.dp)
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(22.dp))
                                    .border(1.dp, Color.Gray),

                                )
                            Spacer(modifier = Modifier.height(30.dp))

                            Text(
                                text = authorInfoState.authorInfo?.name ?: "",
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = authorInfoState.authorInfo?.bio ?: "",
                                fontWeight = FontWeight.Normal,
                                fontSize = 22.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp)
                            )
                        }

                    }

                }

            }
        }

        item {
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


        items(authorQuotesState.authorQuotes) { quote ->

            AuthorQuoteCard(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                quote = quote
            )
        }

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
package com.roshanadke.inspireme.presentation.screen

import android.util.Log
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.roshanadke.inspireme.presentation.ui.theme.BackGroundColor
import com.roshanadke.inspireme.presentation.viewmodel.QuotesViewModel


@OptIn(ExperimentalCoilApi::class)
@Composable
fun AuthorDetailsScreen(
    quotesViewModel: QuotesViewModel = hiltViewModel(),
    authorSlug: String? = null,
    authorName: String? = null,
) {

    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current

    val authorInfo = quotesViewModel.authorInfo.value
    val authorWikipediaInfo = quotesViewModel.authorWikipediaInfo.value

    LaunchedEffect(Unit) {
        authorSlug?.let {
            quotesViewModel.getAuthorInfo(authorSlug)
        }
        authorName?.let {
            quotesViewModel.getAuthorWikipediaInfo(authorName)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = BackGroundColor
            ),
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .padding(18.dp)
                    .size(48.dp)
                    .clickable {
                               backPressedDispatcher.onBackPressedDispatcher
                    },

            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                //image url temp = "https://upload.wikimedia.org/wikipedia/commons/3/3e/Einstein_1921_by_F_Schmutzer_-_restoration.jpg"
                Log.d("TAG", "AuthorDetailsScreen: image url: ${authorWikipediaInfo?.originalImage} ")
                Image(
                    painter = rememberImagePainter(
                        data = authorWikipediaInfo?.originalImage?.source,
                        builder = {
                            crossfade(true)
                        },
                    ),
                    contentDescription = "Author Profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(300.dp)
                        .aspectRatio(1f)
                        .clip(CircleShape)                       // clip to the circle shape
                        .border(0.dp, Color.Gray, CircleShape)

                )
                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = authorInfo?.name ?: "",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = authorInfo?.bio ?: "",
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
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
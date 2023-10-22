package com.roshanadke.inspireme.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.roshanadke.inspireme.R
import com.roshanadke.inspireme.presentation.ui.theme.BackGroundColor
import com.roshanadke.inspireme.presentation.ui.theme.SlateGray

@Composable
fun CategoryLayout(
    onCategoryCardClicked: (category: String) -> Unit
) {

    val categoryList = stringArrayResource(id = R.array.category_list).toList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackGroundColor),
        verticalArrangement = Arrangement.Top,
    ) {

        Spacer(modifier = Modifier.height(20.dp))
        LazyGridItem(
            categoryList,
            onCategoryCardClicked = onCategoryCardClicked

        )
    }
}

@Composable
fun LazyGridItem(
    categoryList: List<String>,
    onCategoryCardClicked: (category: String) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 16.dp,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        content = {
            items(categoryList) { category ->
                CategoryCard(
                    modifier = Modifier
                        .fillMaxWidth(),
                    category = category,
                    onCategoryCardClicked = onCategoryCardClicked

                )
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    )
}


@Composable
fun CategoryCard(
    modifier: Modifier,
    category: String,
    onCategoryCardClicked: (category: String) -> Unit
) {
    Card(
        modifier = modifier.clickable {
            onCategoryCardClicked(category)
        },
        shape = RoundedCornerShape(16.dp),
        /*elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),*/
        colors = CardDefaults.cardColors(containerColor = SlateGray),
    ) {

        Text(
            text = category,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()

        )
    }
}






package com.roshanadke.inspireme.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.roshanadke.inspireme.domain.model.Quote
import com.roshanadke.inspireme.presentation.ui.theme.SlateGray

@Composable
fun AuthorQuoteCard(
    modifier: Modifier,
    quote: Quote,
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        /*elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),*/
        colors = CardDefaults.cardColors(containerColor = SlateGray),
    ) {

        Text(
            text = quote.content,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )


    }

}
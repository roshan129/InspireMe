package com.roshanadke.inspireme.presentation.screen

import com.roshanadke.inspireme.domain.model.Author
import com.roshanadke.inspireme.domain.model.Quote

data class AuthorQuotesState(
    val authorQuotes: List<Quote> = emptyList(),
    val isLoading: Boolean = false,
)

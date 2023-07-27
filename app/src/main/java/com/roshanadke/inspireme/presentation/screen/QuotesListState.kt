package com.roshanadke.inspireme.presentation.screen

import com.roshanadke.inspireme.domain.model.Quote

data class QuotesListState(
    var randomQuotesList: List<Quote> = mutableListOf(),
    var isLoading: Boolean = false
)
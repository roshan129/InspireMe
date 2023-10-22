package com.roshanadke.inspireme.data.dto.author_quotes

import com.roshanadke.inspireme.data.dto.QuoteDto

data class MainQuoteListDto(
    val count: Int?,
    val lastItemIndex: Int?,
    val page: Int?,
    val results: List<QuoteDto>?,
    val totalCount: Int?,
    val totalPages: Int?
)

/*
data class ResultContainer(
    val results: List<QuoteDto>?
)*/

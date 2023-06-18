package com.roshanadke.inspireme.data.dto

import com.roshanadke.inspireme.domain.model.Quote

data class QuoteDto(
    val _id: String,
    val author: String,
    val authorSlug: String,
    val content: String,
    val dateAdded: String,
    val dateModified: String,
    val length: Int,
    val tags: List<String>
) {
    fun toQuote(): Quote {
        return Quote(
            author = author,
            authorSlug = authorSlug,
            content = content,
            length = length,
            tags = tags,
        )
    }
}
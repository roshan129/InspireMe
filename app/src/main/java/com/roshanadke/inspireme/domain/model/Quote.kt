package com.roshanadke.inspireme.domain.model

data class Quote(
    val author: String,
    val authorSlug: String,
    val content: String,
    val length: Int,
    val tags: List<String>
)

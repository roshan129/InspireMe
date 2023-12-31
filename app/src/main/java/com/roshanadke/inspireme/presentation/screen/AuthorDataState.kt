package com.roshanadke.inspireme.presentation.screen

import com.roshanadke.inspireme.domain.model.Author
import com.roshanadke.inspireme.domain.model.Quote

data class AuthorDataState(
    val authorInfo: Author? = null,
    val isLoading: Boolean = false,
)

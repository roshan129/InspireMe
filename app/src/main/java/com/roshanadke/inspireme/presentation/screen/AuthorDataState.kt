package com.roshanadke.inspireme.presentation.screen

import com.roshanadke.inspireme.domain.model.Author

data class AuthorDataState(
    val authorInfo: Author? = null,
    val isLoading: Boolean = false,
)

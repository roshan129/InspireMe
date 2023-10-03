package com.roshanadke.inspireme.domain.repository

import com.roshanadke.inspireme.common.Resource
import com.roshanadke.inspireme.data.dto.QuoteDto
import com.roshanadke.inspireme.domain.model.Author
import com.roshanadke.inspireme.domain.model.AuthorWikipediaInfo
import com.roshanadke.inspireme.domain.model.Quote
import kotlinx.coroutines.flow.Flow

interface AuthorRepository {

    fun getAuthorInfo(authorSlug: String): Flow<Resource<Author>>

    fun getAuthorWikipediaInfo(authorName: String): Flow<Resource<AuthorWikipediaInfo>>

    fun getAuthorQuotes(authorSlug: String): Flow<Resource<List<Quote>>>


}
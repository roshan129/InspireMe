package com.roshanadke.inspireme.domain.repository

import com.roshanadke.inspireme.common.Resource
import com.roshanadke.inspireme.data.dto.AuthorInfoListDto
import com.roshanadke.inspireme.domain.model.Author
import com.roshanadke.inspireme.domain.model.Quote
import kotlinx.coroutines.flow.Flow

interface QuotesRepository {

    fun getSingleRandomQuote(): Flow<Resource<Quote>>

    fun getRandomQuotes(limit: Int): Flow<Resource<List<Quote>>>

    fun getAuthorInfo(authorSlug: String): Flow<Resource<Author>>

}
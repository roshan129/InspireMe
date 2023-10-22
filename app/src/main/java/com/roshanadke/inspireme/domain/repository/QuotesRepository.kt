package com.roshanadke.inspireme.domain.repository

import com.roshanadke.inspireme.common.Resource
import com.roshanadke.inspireme.data.dto.AuthorInfoListDto
import com.roshanadke.inspireme.domain.model.Author
import com.roshanadke.inspireme.domain.model.Quote
import kotlinx.coroutines.flow.Flow

interface QuotesRepository {

    fun getSingleRandomQuote(): Flow<Resource<Quote>>

    fun getRandomQuotes(limit: Int, tag: String): Flow<Resource<List<Quote>>>

    fun getQuotes(limit: Int, tag: String, pageNumber: Int): Flow<Resource<List<Quote>>>

    fun getQuotesByCategory(tag: String): Flow<Resource<List<Quote>>>

}
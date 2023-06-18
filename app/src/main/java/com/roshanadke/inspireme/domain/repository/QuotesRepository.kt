package com.roshanadke.inspireme.domain.repository

import com.roshanadke.inspireme.common.Resource
import com.roshanadke.inspireme.domain.model.Quote
import kotlinx.coroutines.flow.Flow

interface QuotesRepository {

    fun getSingleRandomQuote(): Flow<Resource<Quote>>

    fun getRandomQuotes(): Flow<Resource<List<Quote>>>

}
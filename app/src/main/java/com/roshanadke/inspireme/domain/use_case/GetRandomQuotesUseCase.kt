package com.roshanadke.inspireme.domain.use_case

import com.roshanadke.inspireme.common.Resource
import com.roshanadke.inspireme.domain.model.Quote
import com.roshanadke.inspireme.domain.repository.QuotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRandomQuotesUseCase(
    private val quotesRepository: QuotesRepository
) {

    operator fun invoke(limit: Int): Flow<Resource<List<Quote>>> {
        return quotesRepository.getRandomQuotes(limit)
    }

}
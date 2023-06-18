package com.roshanadke.inspireme.domain.use_case

import com.roshanadke.inspireme.common.Resource
import com.roshanadke.inspireme.domain.model.Quote
import com.roshanadke.inspireme.domain.repository.QuotesRepository
import kotlinx.coroutines.flow.Flow


class GetSingleRandomQuoteUseCase(
    private val quotesRepository: QuotesRepository
) {

    operator fun invoke(): Flow<Resource<Quote>> {
        return quotesRepository.getSingleRandomQuote()
    }


}
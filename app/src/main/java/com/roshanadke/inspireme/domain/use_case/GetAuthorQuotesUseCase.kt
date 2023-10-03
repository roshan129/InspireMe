package com.roshanadke.inspireme.domain.use_case

import com.roshanadke.inspireme.common.Resource
import com.roshanadke.inspireme.domain.model.Quote
import com.roshanadke.inspireme.domain.repository.AuthorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAuthorQuotesUseCase(
    private val authorRepository: AuthorRepository
) {

    operator fun invoke(authorSlug: String): Flow<Resource<List<Quote>>> {
        if(authorSlug.isEmpty()) {
            return flow {  }
        }
        return authorRepository.getAuthorQuotes(authorSlug)
    }
    
}
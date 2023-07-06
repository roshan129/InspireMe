package com.roshanadke.inspireme.domain.use_case

import com.roshanadke.inspireme.common.Resource
import com.roshanadke.inspireme.domain.model.Author
import com.roshanadke.inspireme.domain.repository.AuthorRepository
import com.roshanadke.inspireme.domain.repository.QuotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAuthorInfoUseCase(
    private val authorRepository: AuthorRepository
) {

    operator fun invoke(authorSlug: String): Flow<Resource<Author>> {
        if(authorSlug.isEmpty()) {
            return flow {  }
        }
        return authorRepository.getAuthorInfo(authorSlug)
    }

}
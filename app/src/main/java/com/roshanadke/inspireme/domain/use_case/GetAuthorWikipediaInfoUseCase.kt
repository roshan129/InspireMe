package com.roshanadke.inspireme.domain.use_case

import com.roshanadke.inspireme.common.Resource
import com.roshanadke.inspireme.domain.model.AuthorWikipediaInfo
import com.roshanadke.inspireme.domain.repository.AuthorRepository

class GetAuthorWikipediaInfoUseCase(
    private val repository: AuthorRepository
) {

    operator fun invoke(authorName: String): kotlinx.coroutines.flow.Flow<Resource<AuthorWikipediaInfo>> {
        return repository.getAuthorWikipediaInfo(authorName)
    }

}
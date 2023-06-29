package com.roshanadke.inspireme.data.repository

import com.roshanadke.inspireme.common.Constants
import com.roshanadke.inspireme.common.Resource
import com.roshanadke.inspireme.data.dto.AuthorInfoListDto
import com.roshanadke.inspireme.data.network.InspireMeApiService
import com.roshanadke.inspireme.domain.model.Author
import com.roshanadke.inspireme.domain.model.Quote
import com.roshanadke.inspireme.domain.repository.QuotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.lang.Exception

class QuotesRepositoryImpl(
    private val inspireMeApi: InspireMeApiService
) : QuotesRepository {
    override fun getSingleRandomQuote(): Flow<Resource<Quote>> = flow {
        emit(Resource.Loading())

        try {
            val result = inspireMeApi.getSingleRandomQuote()
            emit(Resource.Success(result.toQuote()))

        } catch (e: IOException) {
            emit(Resource.Error(message = "Please check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Some unexpected error occurred"))
        }


    }

    override fun getRandomQuotes(limit: Int): Flow<Resource<List<Quote>>> = flow {
        emit(Resource.Loading())

        try {
            val results = inspireMeApi.getRandomQuotes(limit)
            emit(Resource.Success(results.map { it.toQuote() }))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Please check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Some unexpected error occurred"))
        }

    }

    override fun getAuthorInfo(authorSlug: String): Flow<Resource<Author>> = flow {
        emit(Resource.Loading())
        try {
            val result  = inspireMeApi.getAuthorInfo(authorSlug)
            if(result.authorInfoDtoList.isNotEmpty()) {
                emit(Resource.Success(result.authorInfoDtoList[0].toAuthor()))
            } else {
                emit(Resource.Error(message = "No data received from server"))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = "Please check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Some unexpected error occurred"))
        }

    }
}
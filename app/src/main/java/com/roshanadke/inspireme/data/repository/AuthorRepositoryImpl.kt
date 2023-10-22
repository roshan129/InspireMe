package com.roshanadke.inspireme.data.repository

import com.roshanadke.inspireme.common.Resource
import com.roshanadke.inspireme.data.network.InspireMeApiService
import com.roshanadke.inspireme.data.network.WikipediaAuthorInfoApiService
import com.roshanadke.inspireme.domain.model.Author
import com.roshanadke.inspireme.domain.model.AuthorWikipediaInfo
import com.roshanadke.inspireme.domain.model.Quote
import com.roshanadke.inspireme.domain.repository.AuthorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class AuthorRepositoryImpl(
    private val inspireMeApi: InspireMeApiService,
    private val wikipediaAuthorInfoApiService: WikipediaAuthorInfoApiService,

    ): AuthorRepository {

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

    override fun getAuthorWikipediaInfo(authorName: String): Flow<Resource<AuthorWikipediaInfo>> = flow {
        emit(Resource.Loading())

        try {

            val result = wikipediaAuthorInfoApiService.getAuthorInfoFromWikipedia(authorName)
            emit(Resource.Success(result.toAuthorWikipediaInfo()))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Please check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Some unexpected error occurred"))
        }
    }

    override fun getAuthorQuotes(authorSlug: String): Flow<Resource<List<Quote>>> = flow {
        emit(Resource.Loading())
        try {
            val result = inspireMeApi.getAuthorQuotes(authorSlug)
            val list = result.results?.map { it.toQuote() } ?: emptyList()
            emit(Resource.Success(list))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Please check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Some unexpected error occurred"))
        }
    }
}
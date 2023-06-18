package com.roshanadke.inspireme.data.repository

import com.roshanadke.inspireme.common.Resource
import com.roshanadke.inspireme.data.network.InspireMeApiService
import com.roshanadke.inspireme.domain.model.Quote
import com.roshanadke.inspireme.domain.repository.QuotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.lang.Exception

class QuotesRepositoryImpl(
    private val inspireMeApi: InspireMeApiService
): QuotesRepository {
    override fun getSingleRandomQuote(): Flow<Resource<Quote>> = flow {
        emit(Resource.Loading())

        try {
            val result  = inspireMeApi.getSingleRandomQuote()
            emit(Resource.Success(result.toQuote()))

        } catch (e: IOException) {
            emit(Resource.Error(message = "Please check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Some unexpected error occurred"))
        }


    }

    override fun getRandomQuotes(): Flow<Resource<List<Quote>>> = flow {
        emit(Resource.Loading())

    }
}
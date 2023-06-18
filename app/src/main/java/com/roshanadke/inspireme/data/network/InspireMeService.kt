package com.roshanadke.inspireme.data.network

import com.roshanadke.inspireme.data.dto.QuoteDto
import retrofit2.http.GET
import retrofit2.http.Query

interface InspireMeApiService {

    companion object {
        const val BASE_URL = "https://api.quotable.io/"
    }


    @GET("/random")
    suspend fun getSingleRandomQuote(): QuoteDto

    @GET("/quotes/random")
    suspend fun getRandomQuotes(
        @Query("limit") limit: Int
    ): List<QuoteDto>

}
package com.roshanadke.inspireme.data.network

import com.roshanadke.inspireme.data.dto.AuthorInfoListDto
import com.roshanadke.inspireme.data.dto.QuoteDto
import com.roshanadke.inspireme.data.dto.author_quotes.AuthorQuoteListDto
import com.roshanadke.inspireme.data.dto.author_wikipedia_info.AuthorWikipediaInfoDto
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

    @GET("/authors")
    suspend fun getAuthorInfo(
        @Query("slug") authorSlug: String
    ): AuthorInfoListDto

    @GET("/quotes")
    suspend fun getAuthorQuotes(
        @Query("author") authorSlug: String
    ): AuthorQuoteListDto

}
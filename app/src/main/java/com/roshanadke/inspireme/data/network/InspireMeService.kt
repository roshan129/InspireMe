package com.roshanadke.inspireme.data.network

import android.health.connect.datatypes.units.Length
import com.roshanadke.inspireme.common.Constants
import com.roshanadke.inspireme.data.dto.AuthorInfoListDto
import com.roshanadke.inspireme.data.dto.QuoteDto
import com.roshanadke.inspireme.data.dto.author_quotes.MainQuoteListDto
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
        @Query("limit") limit: Int,
        @Query("tags") tag: String
    ): List<QuoteDto>

    @GET("/quotes/")
    suspend fun getQuotes(
        @Query("limit") limit: Int,
        @Query("tags") tag: String,
        @Query("page") page: Int = 1,
        @Query("maxLength") maxLength: Int = 300,
    ): MainQuoteListDto


    @GET("/authors")
    suspend fun getAuthorInfo(
        @Query("slug") authorSlug: String
    ): AuthorInfoListDto

    @GET("/quotes")
    suspend fun getAuthorQuotes(
        @Query("author") authorSlug: String
    ): MainQuoteListDto

    @GET("/quotes/random")
    suspend fun getQuotesByCategory(
        @Query("limit") limit: Int = Constants.RANDOM_QUOTES_API_LIMIT,
        @Query("tags") tag: String
    ): List<QuoteDto>

}
package com.roshanadke.inspireme.data.network

import com.roshanadke.inspireme.data.dto.author_wikipedia_info.AuthorWikipediaInfoDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface WikipediaAuthorInfoApiService {

    companion object {
        const val BASE_WIKIPEDIA_URL = "https://en.wikipedia.org/"
    }

    @GET("api/rest_v1/page/summary/{author_name}")
    suspend fun getAuthorInfoFromWikipedia(
        @Path("author_name") authorName: String
    ): AuthorWikipediaInfoDto

}
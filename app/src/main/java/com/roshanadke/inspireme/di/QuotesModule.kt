package com.roshanadke.inspireme.di

import android.app.Application
import android.util.Log
import com.roshanadke.inspireme.domain.connectivity.ConnectivityObserver
import com.roshanadke.inspireme.data.connectivity.NetworkConnectivityObserver
import com.roshanadke.inspireme.data.network.InspireMeApiService
import com.roshanadke.inspireme.data.network.WikipediaAuthorInfoApiService
import com.roshanadke.inspireme.data.repository.AuthorRepositoryImpl
import com.roshanadke.inspireme.data.repository.QuotesRepositoryImpl
import com.roshanadke.inspireme.domain.repository.AuthorRepository
import com.roshanadke.inspireme.domain.repository.QuotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class QuotesModule {

    @Provides
    @Singleton
    fun getInspireMeApiService(okHttpClient: OkHttpClient): InspireMeApiService {
        return Retrofit.Builder()
            .baseUrl(InspireMeApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(InspireMeApiService::class.java)
    }

    @Provides
    @Singleton
    fun getWikipediaAuthorInfoService(okHttpClient: OkHttpClient): WikipediaAuthorInfoApiService {
        return Retrofit.Builder()
            .baseUrl(WikipediaAuthorInfoApiService.BASE_WIKIPEDIA_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(WikipediaAuthorInfoApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor.Logger { message ->
            Log.d("RetrofitLog", message)
        }
        return HttpLoggingInterceptor(logger).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun getQuotesRepository(api: InspireMeApiService): QuotesRepository {
        return QuotesRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun getAuthorRepository(
        api: InspireMeApiService,
        wikiApi: WikipediaAuthorInfoApiService,
    ): AuthorRepository {
        return AuthorRepositoryImpl(api, wikiApi)
    }

    @Provides
    @Singleton
    fun provideNetworkObserver(
        context: Application
    ): ConnectivityObserver {
        return NetworkConnectivityObserver(context)
    }

}
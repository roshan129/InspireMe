package com.roshanadke.inspireme.di

import com.roshanadke.inspireme.domain.repository.QuotesRepository
import com.roshanadke.inspireme.domain.use_case.GetRandomQuotesUseCase
import com.roshanadke.inspireme.domain.use_case.GetSingleRandomQuoteUseCase
import com.roshanadke.inspireme.domain.use_case.QuotesUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideQuotesUseCases(
        quotesRepository: QuotesRepository
    ): QuotesUseCases {
        return QuotesUseCases(
            GetSingleRandomQuoteUseCase(quotesRepository),
            GetRandomQuotesUseCase(quotesRepository)
        )
    }


}
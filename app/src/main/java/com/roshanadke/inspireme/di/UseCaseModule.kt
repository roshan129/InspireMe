package com.roshanadke.inspireme.di

import com.roshanadke.inspireme.domain.repository.AuthorRepository
import com.roshanadke.inspireme.domain.repository.QuotesRepository
import com.roshanadke.inspireme.domain.use_case.AuthorUseCases
import com.roshanadke.inspireme.domain.use_case.GetAuthorInfoUseCase
import com.roshanadke.inspireme.domain.use_case.GetAuthorQuotesUseCase
import com.roshanadke.inspireme.domain.use_case.GetAuthorWikipediaInfoUseCase
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

    @Provides
    @Singleton
    fun provideAuthorUseCases(
        authorRepository: AuthorRepository
    ): AuthorUseCases {
        return AuthorUseCases(
            GetAuthorInfoUseCase(authorRepository),
            GetAuthorWikipediaInfoUseCase(authorRepository),
            GetAuthorQuotesUseCase(authorRepository)
        )
    }


}
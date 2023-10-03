package com.roshanadke.inspireme.domain.use_case

data class AuthorUseCases(
  val getAuthorInfo: GetAuthorInfoUseCase,
  val getAuthorWikipediaInfo: GetAuthorWikipediaInfoUseCase,
  val getAuthorQuotes: GetAuthorQuotesUseCase,
)
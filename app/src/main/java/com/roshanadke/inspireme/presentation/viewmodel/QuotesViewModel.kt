package com.roshanadke.inspireme.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roshanadke.inspireme.common.Constants
import com.roshanadke.inspireme.common.Resource
import com.roshanadke.inspireme.domain.model.AuthorWikipediaInfo
import com.roshanadke.inspireme.domain.model.Quote
import com.roshanadke.inspireme.domain.repository.AuthorRepository
import com.roshanadke.inspireme.domain.repository.QuotesRepository
import com.roshanadke.inspireme.domain.use_case.AuthorUseCases
import com.roshanadke.inspireme.presentation.screen.AuthorDataState
import com.roshanadke.inspireme.presentation.screen.AuthorQuotesState
import com.roshanadke.inspireme.presentation.screen.QuotesListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    private val quotesRepository: QuotesRepository,
    private val authorRepository: AuthorRepository
) : ViewModel() {

    private var _singleQuote: MutableState<Quote?> = mutableStateOf(null)
    val singleQuote: State<Quote?> = _singleQuote

    private var _quotesCategory: MutableState<String> = mutableStateOf("")
    val quotesCategory: State<String> = _quotesCategory

    private var _authorWikipediaInfo: MutableState<AuthorWikipediaInfo?> = mutableStateOf(null)
    val authorWikipediaInfo: State<AuthorWikipediaInfo?> = _authorWikipediaInfo

    private var _selectedAuthorName: MutableState<String> = mutableStateOf("")
    val selectedAuthorName: State<String> = _selectedAuthorName

    private var _quotesListState: MutableState<QuotesListState> = mutableStateOf(QuotesListState())
    var quotesListState: State<QuotesListState> = _quotesListState

    private val _authorDataState = MutableStateFlow(AuthorDataState())
    val authorDataState = _authorDataState.asStateFlow()

    private val _authorQuotesState = MutableStateFlow(AuthorQuotesState())
    val authorQuotesState = _authorQuotesState.asStateFlow()

    init {
        /*getSingleQuote()
        getRandomQuotes()*/
        //getRandomQuotes()
        //getAuthorInfo("ovid")
        //getAuthorWikipediaInfo("14th_Dalai_Lama")
    }

    fun changeSelectedAuthorName(authorName: String) {
        _selectedAuthorName.value = authorName
    }

    fun changeCategory(tag: String) {
        _quotesCategory.value = tag
    }

    fun getSingleQuote() {
        quotesRepository.getSingleRandomQuote().onEach {
            when (it) {
                is Resource.Error -> {
                    Log.d("TAG", "getSingleQuote: error")
                }

                is Resource.Loading -> {
                    Log.d("TAG", "getSingleQuote: loading")
                }

                is Resource.Success -> {
                    Log.d("TAG", "getSingleQuote: in success ")
                    Log.d("TAG", "getSingleQuote: in success: ${it.data?.author} ")
                    _singleQuote.value = it.data
                }
            }
        }.launchIn(viewModelScope)

    }

    fun getRandomQuotes() {
        quotesRepository.getRandomQuotes(
            Constants.RANDOM_QUOTES_API_LIMIT,
            quotesCategory.value
        ).onEach {
            when (it) {
                is Resource.Error -> {
                    _quotesListState.value = _quotesListState.value.copy(
                        isLoading = false
                    )
                    //show error message
                }

                is Resource.Loading -> {
                    _quotesListState.value = _quotesListState.value.copy(
                        isLoading = true
                    )
                }

                is Resource.Success -> {
                    _quotesListState.value = _quotesListState.value.copy(
                        randomQuotesList = it.data ?: emptyList(),
                        isLoading = false
                    )
                }
            }

        }.launchIn(viewModelScope)


    }

    fun getAuthorInfo(authorSlug: String) {

        authorRepository.getAuthorInfo(authorSlug).onEach { result ->

            when (result) {
                is Resource.Loading -> {
                    _authorDataState.value = _authorDataState.value.copy(
                        isLoading = true
                    )
                }

                is Resource.Error -> {
                    _authorDataState.value = _authorDataState.value.copy(
                        isLoading = false
                    )
                }

                is Resource.Success -> {
                    _authorDataState.value = _authorDataState.value.copy(
                        authorInfo = result.data,
                        isLoading = false
                    )
                }
            }

        }.launchIn(viewModelScope)

    }

    fun getAuthorWikipediaInfo(authorName: String) {
        authorRepository.getAuthorWikipediaInfo(authorName).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d("TAG", "getAuthorInfo: loading")
                }

                is Resource.Error -> {
                    Log.d("TAG", "getAuthorInfo: error: ${result.message}")
                }

                is Resource.Success -> {
                    Log.d("TAG", "getAuthorInfo: success")
                    _authorWikipediaInfo.value = result.data
                }
            }

        }.launchIn(viewModelScope)

    }

    fun getAuthorQuotes(authorSlug: String) {
        authorRepository.getAuthorQuotes(authorSlug).onEach { result ->

            when (result) {
                is Resource.Error -> {
                    _authorQuotesState.value = _authorQuotesState.value.copy(
                        isLoading = false
                    )
                }

                is Resource.Loading -> {
                    _authorQuotesState.value = _authorQuotesState.value.copy(
                        isLoading = true
                    )
                }

                is Resource.Success -> {
                    _authorQuotesState.value = _authorQuotesState.value.copy(
                        authorQuotes = result.data ?: emptyList(),
                        isLoading = false
                    )
                }
            }

        }.launchIn(viewModelScope)

    }

    fun getQuotesByCategory(tag: String) {
        quotesRepository.getQuotesByCategory(tag).onEach {
            when (it) {
                is Resource.Error -> {
                    Log.d("TAG", "getQuotesByCategory: error ")

                }

                is Resource.Loading -> {
                    Log.d("TAG", "getQuotesByCategory: loading ")

                }

                is Resource.Success -> {
                    Log.d("TAG", "getQuotesByCategory: success ")
                    Log.d("TAG", "getQuotesByCategory: category quotes: ${it.data?.size} ")
                }
            }
        }.launchIn(viewModelScope)
    }


}
package com.roshanadke.inspireme.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roshanadke.inspireme.R
import com.roshanadke.inspireme.common.Constants
import com.roshanadke.inspireme.common.Resource
import com.roshanadke.inspireme.common.UiEvent
import com.roshanadke.inspireme.common.UiText
import com.roshanadke.inspireme.domain.model.AuthorWikipediaInfo
import com.roshanadke.inspireme.domain.model.Quote
import com.roshanadke.inspireme.domain.repository.AuthorRepository
import com.roshanadke.inspireme.domain.repository.QuotesRepository
import com.roshanadke.inspireme.presentation.screen.AuthorDataState
import com.roshanadke.inspireme.presentation.screen.AuthorQuotesState
import com.roshanadke.inspireme.presentation.screen.QuotesListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Locale.Category
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

    private var _selectedAuthorName: MutableState<String> = mutableStateOf("")
    val selectedAuthorName: State<String> = _selectedAuthorName

    private var _quotesListState: MutableState<QuotesListState> = mutableStateOf(QuotesListState())
    var quotesListState: State<QuotesListState> = _quotesListState

    private val _pageNumber = MutableStateFlow(1)
    val pageNumber = _pageNumber.asStateFlow()

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore = _isLoadingMore.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _shouldResetQuotesListEvent = MutableSharedFlow<Boolean>()
    val shouldResetQuotesList = _shouldResetQuotesListEvent.asSharedFlow()


    fun changeSelectedAuthorName(authorName: String) {
        _selectedAuthorName.value = authorName
    }

    fun changeCategory(tag: String) {
        _quotesCategory.value = tag
        _pageNumber.value = 1
    }

    fun isCategoryChanged(category: String): Boolean {
        return quotesCategory.value != category
    }

    fun resetQuotesList(flag: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _shouldResetQuotesListEvent.emit(flag)
        }
    }

    fun loadMore() {
        if(!isLoadingMore.value) {
            _pageNumber.value++
            loadMoreQuotes()
        }
    }


    fun getQuotes() {
        quotesRepository.getQuotes(
            Constants.RANDOM_QUOTES_API_LIMIT,
            quotesCategory.value,
            pageNumber.value
        ).onEach {
            when (it) {
                is Resource.Error -> {
                    _quotesListState.value = _quotesListState.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            UiText.StringResource(
                                R.string.something_went_wrong
                            ))
                    )
                }

                is Resource.Loading -> {
                    _quotesListState.value = _quotesListState.value.copy(
                        isLoading = true
                    )
                }

                is Resource.Success -> {
                    resetQuotesList(true)
                    _quotesListState.value = _quotesListState.value.copy(
                        randomQuotesList = it.data ?: emptyList(),
                        isLoading = false
                    )
                }
            }

        }.launchIn(viewModelScope)

    }
    private fun loadMoreQuotes() {
        quotesRepository.getQuotes(
            Constants.RANDOM_QUOTES_API_LIMIT,
            quotesCategory.value,
            pageNumber.value
        ).onEach {
            when (it) {
                is Resource.Error -> {
                    _isLoadingMore.value = false
                }

                is Resource.Loading -> {
                    _isLoadingMore.value = true
                }

                is Resource.Success -> {
                    _quotesListState.value = _quotesListState.value.copy(
                        randomQuotesList = _quotesListState.value.randomQuotesList + (it.data ?: emptyList()),
                        isLoading = false
                    )
                    _isLoadingMore.value = false

                }
            }

        }.launchIn(viewModelScope)


    }



}
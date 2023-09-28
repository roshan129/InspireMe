package com.roshanadke.inspireme.presentation.viewmodel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roshanadke.inspireme.common.Constants
import com.roshanadke.inspireme.common.Resource
import com.roshanadke.inspireme.domain.model.Author
import com.roshanadke.inspireme.domain.model.AuthorWikipediaInfo
import com.roshanadke.inspireme.domain.model.Quote
import com.roshanadke.inspireme.domain.use_case.AuthorUseCases
import com.roshanadke.inspireme.domain.use_case.QuotesUseCases
import com.roshanadke.inspireme.presentation.screen.AuthorDataState
import com.roshanadke.inspireme.presentation.screen.QuotesListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    private val quotesUseCases: QuotesUseCases,
    private val authorUseCases: AuthorUseCases,
) : ViewModel() {

    private var _singleQuote: MutableState<Quote?> = mutableStateOf(null)
    val singleQuote: State<Quote?> = _singleQuote

   /* private var _authorInfo: MutableState<Author?> = mutableStateOf(null)
    val authorInfo: State<Author?> = _authorInfo*/

    private var _authorWikipediaInfo: MutableState<AuthorWikipediaInfo?> = mutableStateOf(null)
    val authorWikipediaInfo: State<AuthorWikipediaInfo?> = _authorWikipediaInfo

    /*private var _randomQuotes: MutableState<List<Quote>> = mutableStateOf(emptyList())
    val randomQuotes: State<List<Quote>> = _randomQuotes*/

    private var _selectedAuthorName: MutableState<String> = mutableStateOf("")
    val selectedAuthorName: State<String> = _selectedAuthorName

    private var _quotesListState: MutableState<QuotesListState> = mutableStateOf(QuotesListState())
    var quotesListState: State<QuotesListState> = _quotesListState

  /*  private var _authorDataState: MutableState<AuthorDataState> = mutableStateOf(AuthorDataState())
    var authorDataState: State<AuthorDataState> = _authorDataState*/

    private val _authorDataState = MutableStateFlow(AuthorDataState())
    val authorDataState = _authorDataState.asStateFlow()


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

    fun getSingleQuote() {

        quotesUseCases.getSingleRandomQuoteUseCase().onEach {
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
        quotesUseCases.getRandomQuotesUseCase(Constants.RANDOM_QUOTES_API_LIMIT)
            .onEach {

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
                        //_randomQuotes.value = it.data ?: emptyList()
                    }
                }

            }.launchIn(viewModelScope)


    }

    fun getAuthorInfo(authorSlug: String) {

        authorUseCases.getAuthorInfo(authorSlug).onEach { result ->

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

        authorUseCases.getAuthorWikipediaInfo(authorName).onEach { result ->

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


}

private fun checkPermission(context: Context): Boolean {
    val result = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    return result == PackageManager.PERMISSION_GRANTED
}

package com.roshanadke.inspireme.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roshanadke.inspireme.common.Constants
import com.roshanadke.inspireme.common.Resource
import com.roshanadke.inspireme.domain.model.Quote
import com.roshanadke.inspireme.domain.use_case.GetSingleRandomQuoteUseCase
import com.roshanadke.inspireme.domain.use_case.QuotesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    private val quotesUseCases: QuotesUseCases
) : ViewModel() {

    private var _singleQuote: MutableState<Quote?> = mutableStateOf(null)
    val singleQuote: State<Quote?> = _singleQuote

    private var _randomQuotes: MutableState<List<Quote>> = mutableStateOf(emptyList())
    val randomQuotes: State<List<Quote>> = _randomQuotes

    init {
        /*getSingleQuote()
        getRandomQuotes()*/
    }

    fun getSingleQuote() {

        quotesUseCases.getSingleRandomQuoteUseCase().onEach {
            when(it) {
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

                when(it) {
                    is Resource.Error -> {
                        Log.d("TAG", "getRandomQuotes: error")
                    }
                    is Resource.Loading -> {
                        Log.d("TAG", "getRandomQuotes: loading")
                    }
                    is Resource.Success -> {
                        Log.d("TAG", "getRandomQuotes: in success ")
                        _randomQuotes.value = it.data ?: emptyList()
                    }
                }

            }.launchIn(viewModelScope)


    }


}
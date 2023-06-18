package com.roshanadke.inspireme.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roshanadke.inspireme.common.Resource
import com.roshanadke.inspireme.domain.model.Quote
import com.roshanadke.inspireme.domain.use_case.GetSingleRandomQuoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    private val getSingleRandomQuoteUseCase: GetSingleRandomQuoteUseCase
) : ViewModel() {

    private var _singleQuote: MutableState<Quote?> = mutableStateOf(null)
    val singleQuote: State<Quote?> = _singleQuote

    init {
        getSingleQuote()
    }

    fun getSingleQuote() {

        getSingleRandomQuoteUseCase().onEach {
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


}
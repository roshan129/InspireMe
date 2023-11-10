package com.roshanadke.inspireme.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roshanadke.inspireme.R
import com.roshanadke.inspireme.common.Resource
import com.roshanadke.inspireme.common.UiEvent
import com.roshanadke.inspireme.common.UiText
import com.roshanadke.inspireme.domain.model.AuthorWikipediaInfo
import com.roshanadke.inspireme.domain.repository.AuthorRepository
import com.roshanadke.inspireme.presentation.screen.AuthorDataState
import com.roshanadke.inspireme.presentation.screen.AuthorQuotesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AuthorViewModel @Inject constructor(
    private val authorRepository: AuthorRepository
) : ViewModel() {


    private val _authorDataState = MutableStateFlow(AuthorDataState())
    val authorDataState = _authorDataState.asStateFlow()

    private val _authorQuotesState = MutableStateFlow(AuthorQuotesState())
    val authorQuotesState = _authorQuotesState.asStateFlow()

    private var _authorWikipediaInfo: MutableState<AuthorWikipediaInfo?> = mutableStateOf(null)
    val authorWikipediaInfo: State<AuthorWikipediaInfo?> = _authorWikipediaInfo

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


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
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            UiText.StringResource(
                            R.string.something_went_wrong
                        ))
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
                    //do nothing
                }

                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            UiText.StringResource(
                                R.string.something_went_wrong
                            ))
                    )
                }

                is Resource.Success -> {
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
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            UiText.StringResource(
                                R.string.something_went_wrong
                            ))
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

}
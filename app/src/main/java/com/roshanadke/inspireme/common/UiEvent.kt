package com.roshanadke.inspireme.common

sealed class UiEvent {
    data class ShowSnackbar(val message: UiText): UiEvent()
}
package com.example.dictionary_app.feature_dictionary.presentation

sealed class UiEvent {
    data class ShowSnackbar(val message: String): UiEvent()

}

package com.example.androidtemplateapp.ui.common.snackbar

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SnackbarViewModel : ViewModel() {

    private val _messageIds: MutableStateFlow<List<Int>> = MutableStateFlow(emptyList())
    val messageIds: StateFlow<List<Int>> = _messageIds.asStateFlow()

    fun showUserMessage(@StringRes messageId: Int) {
        _messageIds.update { currentMessageIds ->
            currentMessageIds + messageId
        }
    }

    fun clearMessages() {
        _messageIds.value = emptyList()
    }
}
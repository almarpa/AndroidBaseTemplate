package com.example.androidtemplateapp.ui.common.snackbar

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

data class SnackbarEvent(
    val message: String,
    val action: SnackbarAction? = null,
)

data class SnackbarAction(
    val name: String,
    val action: () -> Unit,
)

object SnackbarController {

    private val _snackbarEvents = Channel<SnackbarEvent>()
    val snackbarEvents = _snackbarEvents.receiveAsFlow()

    suspend fun sendSnackbarEvent(event: SnackbarEvent) {
        _snackbarEvents.send(event)
    }
}
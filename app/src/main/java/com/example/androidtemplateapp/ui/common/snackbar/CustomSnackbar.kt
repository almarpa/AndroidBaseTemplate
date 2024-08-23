package com.example.androidtemplateapp.ui.common.snackbar

import android.annotation.SuppressLint
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CustomSnackBar(snackbarHostState: SnackbarHostState) {
    snackbarHostState.currentSnackbarData?.let { data ->
        Snackbar(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            actionColor = MaterialTheme.colorScheme.onPrimary,
            actionContentColor = MaterialTheme.colorScheme.onPrimary,
            snackbarData = data,
        )
    }
}

fun CoroutineScope.showSnackbar(
    snackbarHostState: SnackbarHostState,
    message: String = "SnackBar Preview",
    duration: SnackbarDuration = SnackbarDuration.Short,
    actionLabel: String? = null,
    onActionPerformed: () -> Unit = {},
    onDismissed: () -> Unit = {},
) {
    launch {
        // Dismiss previous existing snackbar
        snackbarHostState.currentSnackbarData?.dismiss()

        val action = snackbarHostState.showSnackbar(
            message = message,
            duration = duration,
            actionLabel = actionLabel,
            withDismissAction = true,
        )

        when (action) {
            SnackbarResult.ActionPerformed -> {
                onActionPerformed()
            }

            SnackbarResult.Dismissed -> {
                onDismissed()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun PreviewCustomSnackBar() {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { CustomSnackBar(snackBarHostState) }
    ) {
        coroutineScope.showSnackbar(
            snackbarHostState = snackBarHostState,
            message = "Scaffold SnackBar Preview",
            actionLabel = "Action",
        )
    }
}
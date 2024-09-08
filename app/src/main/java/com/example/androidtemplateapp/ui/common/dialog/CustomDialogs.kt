package com.example.androidtemplateapp.ui.common.dialog

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.androidtemplateapp.R

@Composable
fun Context.SimpleAlertDialog(
    show: Boolean = true,
    title: String = stringResource(R.string.app_error_title),
    description: String = stringResource(R.string.empty_string),
    onConfirm: () -> Unit = {},
    confirmText: Int = R.string.common_accept,
    onDismissRequest: () -> Unit = {},
) {
    var isActive by rememberSaveable { mutableStateOf(show) }

    if (isActive) {
        AlertDialog(
            onDismissRequest = { onDismissRequest() },
            title = { Text(text = title) },
            text = { Text(text = description) },
            confirmButton = {
                TextButton(
                    onClick = {
                        isActive = false
                        onConfirm()
                    }
                ) {
                    Text(text = getString(confirmText))
                }
            },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            ),
        )
    }
}


@Composable
fun Context.CustomDialog(
    show: Boolean = true,
    title: Int = R.string.empty_string,
    description: Int = R.string.empty_string,
    onConfirm: () -> Unit = {},
    confirmText: Int = R.string.common_accept,
    onCancel: () -> Unit = {},
    cancelText: Int = R.string.common_cancel,
    onDismissRequest: () -> Unit = {},
) {
    if (show) {
        Dialog(
            onDismissRequest = { onDismissRequest() },
            properties = DialogProperties()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(20.dp),
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        text = getString(title)
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 30.dp),
                        fontSize = 16.sp,
                        text = getString(description)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(onClick = { onCancel() }) {
                            Text(text = getString(cancelText))
                        }
                        Button(onClick = { onConfirm() }) {
                            Text(text = getString(confirmText))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SimpleAlertDialogPreview() {
    LocalContext.current.SimpleAlertDialog(
        title = stringResource(R.string.search_title),
        description = stringResource(R.string.error_getting_pokemon_list),
    )
}


@Preview
@Composable
fun CustomDialogPreview() {
    LocalContext.current.CustomDialog(
        title = R.string.search_title,
        description = R.string.error_getting_pokemon_list
    )
}
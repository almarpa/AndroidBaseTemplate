package com.example.androidbasetemplate.ui.common.dialog

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.androidbasetemplate.R

@Composable
fun Context.SimpleAlertDialog(
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
        AlertDialog(
            onDismissRequest = { onDismissRequest() },
            title = { Text(text = getString(title)) },
            text = { Text(text = getString(description)) },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text(text = getString(confirmText))
                }
            },
            dismissButton = {
                TextButton(onClick = { onCancel() }) {
                    Text(text = getString(cancelText))
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
        title = R.string.search_title,
        description = R.string.error_getting_pokemon_list,
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
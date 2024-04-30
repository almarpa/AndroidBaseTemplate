package com.example.androidbasetemplate.ui.common.error

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidbasetemplate.R

@Preview("Error Retry View", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun GenericRetryView(
    errorDescription: Int = R.string.error_getting_pokemon_list,
    action: () -> Unit = {},
) {
    Column(
        modifier = Modifier.padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.secondary,
            text = stringResource(id = R.string.error_getting_pokemon_list),
        )
        Button(
            contentPadding = PaddingValues(16.dp),
            onClick = { action() }
        ) {
            Text(text = stringResource(id = R.string.retry_btn))
        }
    }
}
package com.example.androidbasetemplate.ui.common.notfound

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidbasetemplate.R
import com.example.androidbasetemplate.ui.common.preview.TemplatePreviewTheme

@Composable
fun NotFoundView() {
    Column(
        modifier = Modifier.padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            text = "404",
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 50.sp
        )
        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.secondary,
            text = stringResource(id = R.string.data_not_found),
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview("Not Found View")
@Preview("Not Found View Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun NotFoundViewPreview() {
    TemplatePreviewTheme {
        NotFoundView()
    }
}
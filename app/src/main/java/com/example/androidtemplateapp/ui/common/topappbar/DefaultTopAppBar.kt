package com.example.androidtemplateapp.ui.common.topappbar

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidtemplateapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview("Default Top App Bar Light Mode")
@Preview(
    name = "Default Top App Bar Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_UNDEFINED,
    showBackground = true
)
fun DefaultTopAppBar(
    modifier: Modifier = Modifier,
    title: Int = R.string.settings_title,
    onBackButton: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                stringResource(id = title),
                style = MaterialTheme.typography.titleLarge,
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { onBackButton.invoke() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(R.string.menu_drawer_btn),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState()),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
    )
}

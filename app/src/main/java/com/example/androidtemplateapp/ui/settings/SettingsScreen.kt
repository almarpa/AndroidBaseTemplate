package com.example.androidtemplateapp.ui.settings

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidtemplateapp.R
import com.example.androidtemplateapp.entity.enums.AppTheme
import com.example.androidtemplateapp.ui.common.preview.TemplatePreviewTheme
import com.example.androidtemplateapp.ui.common.topappbar.DefaultTopAppBar

@Composable
fun SettingsScreen(
    themeState: AppTheme,
    getUserAppTheme: () -> Unit,
    onSetUserAppTheme: (Boolean) -> Unit,
    onBackPressed: () -> Unit,
) {
    Scaffold(
        topBar = {
            DefaultTopAppBar(title = R.string.settings_title) { onBackPressed() }
        }
    ) { paddingValues ->
        LaunchedEffect(Unit) { getUserAppTheme() }
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = 16.dp)
                .wrapContentSize()
                .fillMaxWidth(),
        ) {
            DarkModeSection(
                themeState = themeState,
                onChange = { isChecked -> onSetUserAppTheme(isChecked) },
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .height(2.dp)
            )
            AboutSection()
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .height(2.dp)
            )
        }
    }
}

@Composable
fun DarkModeSection(themeState: AppTheme, onChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = stringResource(R.string.dark_mode),
            style = MaterialTheme.typography.titleMedium
        )
        Switch(
            checked = themeState == AppTheme.DARK,
            onCheckedChange = { onChange(it) },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}

@Composable
fun AboutSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = stringResource(R.string.about),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
fun SettingsScreenPreview() {
    TemplatePreviewTheme {
        SettingsScreen(
            themeState = AppTheme.DARK,
            getUserAppTheme = {},
            onSetUserAppTheme = {},
            onBackPressed = {}
        )
    }
}

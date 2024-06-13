package com.example.androidbasetemplate.ui.settings

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.androidbasetemplate.R
import com.example.androidbasetemplate.entity.enums.AppTheme
import com.example.androidbasetemplate.ui.common.mocks.getSettingsViewModelMock
import com.example.androidbasetemplate.ui.common.preview.TemplatePreviewTheme
import com.example.androidbasetemplate.ui.common.topappbar.DefaultTopAppBar

@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
) {
    Scaffold(
        topBar = {
            DefaultTopAppBar(title = R.string.settings_title) { onBackPressed() }
        }
    ) { paddingValues ->
        val themeState by settingsViewModel.themeState.collectAsStateWithLifecycle()
        LaunchedEffect(Unit) { settingsViewModel.getUserAppTheme() }

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = 16.dp)
                .wrapContentSize()
                .fillMaxWidth(),
        ) {
            DarkModeSection(themeState) { isChecked ->
                settingsViewModel.setUserAppTheme(isChecked)
            }
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
fun DarkModeSection(themeState: AppTheme, onSwitchChange: (Boolean) -> Unit) {
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
            onCheckedChange = { onSwitchChange(it) },
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
        SettingsScreen(settingsViewModel = getSettingsViewModelMock()) {}
    }
}

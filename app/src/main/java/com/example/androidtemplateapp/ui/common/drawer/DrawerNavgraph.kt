package com.example.androidtemplateapp.ui.common.drawer

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.androidtemplateapp.ui.common.navigation.NavigationActions
import com.example.androidtemplateapp.ui.common.navigation.Routes
import com.example.androidtemplateapp.ui.settings.SettingsScreen
import com.example.androidtemplateapp.ui.settings.SettingsViewModel

fun NavGraphBuilder.drawerNavGraph(navigationActions: NavigationActions) {
    composable<Routes.Settings>() {
        val settingsViewModel: SettingsViewModel = hiltViewModel()
        val uiState by settingsViewModel.settingsUiState.collectAsStateWithLifecycle()

        SettingsScreen(
            uiState = uiState,
            locales = settingsViewModel.locales,
            onLanguageChange = { settingsViewModel.setUserAppLocale(it) },
            onThemeChange = { isChecked -> settingsViewModel.setUserAppTheme(isChecked) },
            onBackPressed = { navigationActions.navigateBack() },
        )
    }
}

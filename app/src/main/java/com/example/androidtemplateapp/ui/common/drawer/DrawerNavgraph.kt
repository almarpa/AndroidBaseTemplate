package com.example.androidtemplateapp.ui.common.drawer

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.entry
import com.example.androidtemplateapp.ui.common.navigation.NavigationActions
import com.example.androidtemplateapp.ui.common.navigation.Routes
import com.example.androidtemplateapp.ui.settings.SettingsScreen
import com.example.androidtemplateapp.ui.settings.SettingsViewModel

fun <T : Any> EntryProviderBuilder<T>.drawerNavGraph(navigationActions: NavigationActions) {
    entry<Routes.Settings> {
        val settingsViewModel: SettingsViewModel = hiltViewModel()
        val userDataState by settingsViewModel.userData.collectAsStateWithLifecycle()

        SettingsScreen(
            userData = userDataState,
            locales = settingsViewModel.locales,
            onLanguageChange = { settingsViewModel.setUserAppLocale(it) },
            onThemeChange = { isChecked -> settingsViewModel.setUserAppTheme(isChecked) },
            onBackPressed = { navigationActions.navigateBack() },
        )
    }
}

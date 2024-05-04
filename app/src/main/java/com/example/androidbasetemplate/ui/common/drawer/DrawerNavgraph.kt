package com.example.androidbasetemplate.ui.common.drawer

import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.androidbasetemplate.common.utils.getViewModel
import com.example.androidbasetemplate.ui.common.TemplateDestinations
import com.example.androidbasetemplate.ui.settings.SettingsScreen
import com.example.androidbasetemplate.ui.settings.SettingsViewModel

fun NavGraphBuilder.drawerNavGraph(navController: NavHostController) {
    composable(TemplateDestinations.SETTINGS_ROUTE) {
        SettingsScreen(
            settingsViewModel = LocalContext.current.getViewModel<SettingsViewModel>(),
            navController = navController,
        )
    }
}

package com.example.androidbasetemplate.ui.common.drawer

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.androidbasetemplate.common.utils.getViewModel
import com.example.androidbasetemplate.ui.common.TemplateDestinations
import com.example.androidbasetemplate.ui.settings.SettingsScreen
import com.example.androidbasetemplate.ui.settings.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.drawerNavGraph(
    navController: NavHostController,
    drawerState: DrawerState,
) {
    composable(TemplateDestinations.SETTINGS_ROUTE) {
        SettingsScreen(
            settingsViewModel = LocalContext.current.getViewModel<SettingsViewModel>(),
            navController = navController,
            drawerState = drawerState,
        )
    }
}

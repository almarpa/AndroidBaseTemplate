package com.example.androidbasetemplate.ui.common.drawer

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.androidbasetemplate.ui.common.navigation.TemplateDestinations.SETTINGS_ROUTE
import com.example.androidbasetemplate.ui.settings.SettingsScreen

fun NavGraphBuilder.drawerNavGraph(navController: NavHostController) {
    composable(SETTINGS_ROUTE) {
        SettingsScreen { navController.popBackStack() }
    }
}

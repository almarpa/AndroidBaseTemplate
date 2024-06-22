package com.example.androidtemplateapp.ui.common.drawer

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.androidtemplateapp.ui.common.navigation.Routes
import com.example.androidtemplateapp.ui.settings.SettingsScreen

fun NavGraphBuilder.drawerNavGraph(navController: NavHostController) {
    composable(Routes.Settings.route) {
        SettingsScreen { navController.popBackStack() }
    }
}

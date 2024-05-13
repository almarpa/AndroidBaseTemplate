package com.example.androidbasetemplate.ui.common.drawer

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.androidbasetemplate.ui.common.navigation.Routes
import com.example.androidbasetemplate.ui.settings.SettingsScreen

fun NavGraphBuilder.drawerNavGraph(navController: NavHostController) {
    composable(Routes.Settings.route) {
        SettingsScreen { navController.popBackStack() }
    }
}

package com.example.androidbasetemplate.ui

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.androidbasetemplate.MainActivity
import com.example.androidbasetemplate.ui.home.HomeScreen
import com.example.androidbasetemplate.ui.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.templateDrawerNavGraph(
    drawerState: DrawerState,
    navigationActions: TemplateNavigationActions,
) {
    composable(TemplateDestinations.SETTINGS_ROUTE) {
        HomeScreen(
            homeViewModel = ViewModelProvider(LocalContext.current as MainActivity)[HomeViewModel::class.java],
            drawerState = drawerState,
            navigationActions = navigationActions
        )
    }
    composable(TemplateDestinations.ABOUT_ROUTE) {
        HomeScreen(
            homeViewModel = ViewModelProvider(LocalContext.current as MainActivity)[HomeViewModel::class.java],
            drawerState = drawerState,
            navigationActions = navigationActions
        )
    }
}

package com.example.androidtemplateapp.ui

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.rememberNavController
import com.example.androidtemplateapp.ui.common.drawer.Drawer
import com.example.androidtemplateapp.ui.common.navigation.NavigationActions
import com.example.androidtemplateapp.ui.common.navigation.Routes
import kotlinx.coroutines.launch

@Composable
fun TemplateApp() {
    val navController = rememberNavController()
    val navigationActions = remember(navController) { NavigationActions(navController) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val currentRoute = Routes.Splash

    ModalNavigationDrawer(
        drawerContent = {
            Drawer(
                navigateToSettings = navigationActions.navigateToSettings,
                closeDrawer = { coroutineScope.launch { drawerState.close() } },
            )
        },
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
    ) {
        TemplateNavHost(
            navController = navController,
            drawerState = drawerState,
            startDestination = Routes.Splash,
            currentRoute = currentRoute,
            navigationActions = navigationActions,
        )
    }
}

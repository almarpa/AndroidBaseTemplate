package com.example.androidtemplateapp.ui

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation3.runtime.rememberNavBackStack
import com.example.androidtemplateapp.ui.common.drawer.Drawer
import com.example.androidtemplateapp.ui.common.navigation.NavigationActions
import com.example.androidtemplateapp.ui.common.navigation.Routes
import kotlinx.coroutines.launch

@Composable
fun TemplateApp() {
    val coroutineScope = rememberCoroutineScope()
    val currentRoute = Routes.Splash
    val navBackStack = rememberNavBackStack(currentRoute)
    val navigationActions = remember(navBackStack) { NavigationActions(navBackStack) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

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
            navBackStack = navBackStack,
            drawerState = drawerState,
            currentRoute = currentRoute,
            navigationActions = navigationActions,
        )
    }
}

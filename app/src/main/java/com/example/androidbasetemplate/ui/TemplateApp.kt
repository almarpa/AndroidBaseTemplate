package com.example.androidbasetemplate.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateApp() {
    val navController = rememberNavController()
    val navigationActions = remember(navController) { TemplateNavigationActions(navController) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: TemplateDestinations.HOME_ROUTE

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            TemplateDrawer(
                currentRoute = currentRoute,
                navigateToHome = navigationActions.navigateToHome,
                navigateToPokemonList = navigationActions.navigateToPokemonList,
                closeDrawer = { coroutineScope.launch { drawerState.close() } },
            )
        },
        drawerState = drawerState,
    ) {
        Row {
            TemplateNavHost(
                navController = navController,
                drawerState = drawerState,
                navigationActions = navigationActions,
            )
        }
    }
}

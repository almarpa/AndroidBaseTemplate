package com.example.androidbasetemplate.ui

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.androidbasetemplate.ui.common.NavigationActions
import com.example.androidbasetemplate.ui.common.TemplateDestinations
import com.example.androidbasetemplate.ui.common.bottomappbar.bottomAppBarNavGraph
import com.example.androidbasetemplate.ui.common.drawer.drawerNavGraph
import com.example.androidbasetemplate.ui.splash.splashNavGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = TemplateDestinations.SPLASH_ROUTE,
    drawerState: DrawerState,
    currentRoute: String,
    navigationActions: NavigationActions,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        splashNavGraph(navigationActions)
        drawerNavGraph(navController)
        bottomAppBarNavGraph(drawerState, currentRoute, navigationActions)
    }
}

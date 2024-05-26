package com.example.androidbasetemplate.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.androidbasetemplate.ui.common.bottomappbar.bottomAppBarNavGraph
import com.example.androidbasetemplate.ui.common.drawer.drawerNavGraph
import com.example.androidbasetemplate.ui.common.navigation.NavigationActions
import com.example.androidbasetemplate.ui.common.navigation.Routes
import com.example.androidbasetemplate.ui.splash.splashNavGraph

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TemplateNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.Splash.route,
    drawerState: DrawerState,
    currentRoute: String,
    navigationActions: NavigationActions,
) {
    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier,
        ) {
            splashNavGraph(navigationActions)
            drawerNavGraph(navController)
            bottomAppBarNavGraph(drawerState, currentRoute, navigationActions, navController)
        }
    }
}

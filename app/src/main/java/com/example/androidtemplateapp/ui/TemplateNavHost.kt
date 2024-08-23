package com.example.androidtemplateapp.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.androidtemplateapp.ui.common.bottomappbar.bottomAppBarNavGraph
import com.example.androidtemplateapp.ui.common.drawer.drawerNavGraph
import com.example.androidtemplateapp.ui.common.navigation.NavigationActions
import com.example.androidtemplateapp.ui.common.navigation.Routes
import com.example.androidtemplateapp.ui.splash.splashNavGraph

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TemplateNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: Routes,
    drawerState: DrawerState,
    currentRoute: Routes,
    navigationActions: NavigationActions,
) {
    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier,
        ) {
            splashNavGraph(navigationActions)
            drawerNavGraph(navigationActions)
            bottomAppBarNavGraph(drawerState, currentRoute, navigationActions)
        }
    }
}

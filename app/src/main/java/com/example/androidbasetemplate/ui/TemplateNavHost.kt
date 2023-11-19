package com.example.androidbasetemplate.ui

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.androidbasetemplate.ui.common.bottomAppBarNavGraph
import com.example.androidbasetemplate.ui.pokemondetail.pokemonDetailNavGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = TemplateDestinations.HOME_ROUTE,
    drawerState: DrawerState,
    navigationActions: TemplateNavigationActions,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        templateDrawerNavGraph(drawerState, navigationActions)
        bottomAppBarNavGraph(navController, drawerState, navigationActions)
        pokemonDetailNavGraph(drawerState, navigationActions)
    }
}

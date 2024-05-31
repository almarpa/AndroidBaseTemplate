package com.example.androidbasetemplate.ui.common.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.androidbasetemplate.entity.Pokemon


/**
 * Destinations used throughout the app.
 */
sealed class Routes(val route: String) {
    data object Splash : Routes("/splash")
    data object PokemonList : Routes("/pokemonList")
    data object PokemonDetail : Routes("/pokemonDetail/{pokemon}")
    data object Team : Routes("/team")
    data object Settings : Routes("/settings")
}

/**
 * Models the navigation actions in the app.
 */
class NavigationActions(private val navController: NavHostController) {
    val navigateToPokemonList: () -> Unit = {
        navController.navigate(Routes.PokemonList.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
                inclusive = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToTeamList: () -> Unit = {
        navController.navigate(Routes.Team.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToSettings: () -> Unit = {
        navController.navigate(Routes.Settings.route) {
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToDetailNavGraph: (Pokemon) -> Unit = { pokemon ->
        navController.navigate(
            "/pokemonDetail/${pokemon.pokemonToJSONString()}"
        )
    }
}
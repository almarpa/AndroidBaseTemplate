package com.example.androidbasetemplate.ui.common

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

/**
 * Destinations used throughout the app.
 */
object TemplateDestinations {
    const val SPLASH_ROUTE = "/splash"
    const val POKEMON_LIST_ROUTE = "/pokemonList"
    const val FAVORITE_LIST_ROUTE = "/favoriteList"
    const val SETTINGS_ROUTE = "/settings"
}

/**
 * Models the navigation actions in the app.
 */
class NavigationActions(private val navController: NavHostController) {
    val navigateToPokemonList: () -> Unit = {
        navController.navigate(TemplateDestinations.POKEMON_LIST_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
                inclusive = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToFavoriteList: () -> Unit = {
        navController.navigate(TemplateDestinations.FAVORITE_LIST_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToSettings: () -> Unit = {
        navController.navigate(TemplateDestinations.SETTINGS_ROUTE) {
            launchSingleTop = true
            restoreState = true
        }
    }
}

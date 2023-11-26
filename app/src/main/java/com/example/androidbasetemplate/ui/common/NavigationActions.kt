package com.example.androidbasetemplate.ui.common

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

/**
 * Destinations used in the [TemplateApp].
 */
object TemplateDestinations {
    const val HOME_ROUTE = "home"
    const val POKEMON_LIST_ROUTE = "pokemonList"
    const val POKEMON_DETAIL = "pokemonDetail/{pokemonID}"
    const val FAVORITE_LIST_ROUTE = "favoriteList"
    const val SETTINGS_ROUTE = "settings"
}

/**
 * Models the navigation actions in the app.
 */
class NavigationActions(navController: NavHostController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(TemplateDestinations.HOME_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToPokemonList: () -> Unit = {
        navController.navigate(TemplateDestinations.POKEMON_LIST_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
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

    // TODO: separate in different navgraphs
    val navigateToSettings: () -> Unit = {
        navController.navigate(TemplateDestinations.SETTINGS_ROUTE) {
            launchSingleTop = true
            restoreState = true
        }
    }
}

fun NavController.navigateToDetailNavGraph(selectedPokemonID: Int) {
    navigate("pokemonDetail/$selectedPokemonID") {
        launchSingleTop = true
        restoreState = true
    }
}

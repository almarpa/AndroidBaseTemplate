package com.example.androidbasetemplate.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

/**
 * Destinations used in the [TemplateApp].
 */
object TemplateDestinations {
    const val HOME_ROUTE = "home"
    const val POKEMON_LIST = "pokemonList"
    const val POKEMON_DETAIL = "pokemonDetail/{pokemonID}"
}

/**
 * Models the navigation actions in the app.
 */
class TemplateNavigationActions(navController: NavHostController) {
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
        navController.navigate(TemplateDestinations.POKEMON_LIST) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

fun NavController.navigateToDetailNavGraph(selectedPokemonID: Int) {
    navigate("pokemonDetail/$selectedPokemonID") {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

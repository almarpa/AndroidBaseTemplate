package com.example.androidtemplateapp.ui.common.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.androidtemplateapp.entity.Pokemon
import kotlinx.serialization.Serializable


/**
 * Destinations used throughout the app.
 */
sealed interface Routes {
    @Serializable
    data object Splash : Routes

    @Serializable
    data object PokemonList : Routes

    @Serializable
    data object Team : Routes

    @Serializable
    data object Settings : Routes
}

/**
 * Models the navigation actions in the app.
 */
class NavigationActions(private val navController: NavHostController) {
    val navigateToPokemonList: () -> Unit = {
        navController.navigate(Routes.PokemonList) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
                inclusive = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToTeamList: () -> Unit = {
        navController.navigate(Routes.Team) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToSettings: () -> Unit = {
        navController.navigate(Routes.Settings) {
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToDetailNavGraph: (Pokemon) -> Unit = { pokemon ->
        navController.navigate(pokemon)
    }

    val navigateBack: () -> Unit = {
        navController.navigateUp()
    }
}
package com.example.androidtemplateapp.ui.common.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.androidtemplateapp.entity.Pokemon
import kotlinx.serialization.Serializable


/**
 * Destinations used throughout the app.
 */
sealed interface Routes : NavKey {
    @Serializable
    data object Splash : Routes

    @Serializable
    data object PokemonList : Routes

    @Serializable
    data object Team : Routes

    @Serializable
    data object Settings : Routes

    @Serializable
    data class Detail(val pokemon: Pokemon) : Routes
}

/**
 * Models the navigation actions in the app.
 */
class NavigationActions(private val backStack: NavBackStack) {
    val navigateToPokemonList: () -> Unit = { backStack.add(Routes.PokemonList) }
    val navigateToTeamList: () -> Unit = { backStack.add(Routes.Team) }
    val navigateToSettings: () -> Unit = { backStack.add(Routes.Settings) }
    val navigateToDetail: (Pokemon) -> Unit = { pokemon -> backStack.add(Routes.Detail(pokemon)) }
    val navigateBack: () -> Unit = { backStack.removeLastOrNull() }
}
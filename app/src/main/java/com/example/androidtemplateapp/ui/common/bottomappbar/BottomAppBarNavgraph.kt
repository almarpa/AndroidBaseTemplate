package com.example.androidtemplateapp.ui.common.bottomappbar

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.material3.DrawerState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.androidtemplateapp.entity.Pokemon.Companion.getPokemonFromJson
import com.example.androidtemplateapp.ui.common.navigation.NavigationActions
import com.example.androidtemplateapp.ui.common.navigation.Routes
import com.example.androidtemplateapp.ui.pokemonlist.PokemonListScreen
import com.example.androidtemplateapp.ui.pokemonlist.details.PokemonDetailsScreen
import com.example.androidtemplateapp.ui.team.TeamScreen

context(SharedTransitionScope)
@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.bottomAppBarNavGraph(
    drawerState: DrawerState,
    currentRoute: String,
    navigationActions: NavigationActions,
    navController: NavController,
) {
    composable(Routes.PokemonList.route) {
        PokemonListScreen(
            animatedVisibilityScope = this,
            drawerState = drawerState,
            currentRoute = currentRoute,
            navigationActions = navigationActions,
        )
    }
    composable(Routes.Team.route) {
        TeamScreen(
            drawerState = drawerState,
            currentRoute = currentRoute,
            navigationActions = navigationActions,
        )
    }
    composable(
        route = Routes.PokemonDetail.route,
        arguments = listOf(
            navArgument("pokemon") {
                type = NavType.StringType
                nullable = false
            },
        ),
    ) { navBackStackEntry ->
        // TODO: use typed safe navigation (PokemonNavType)
        navBackStackEntry.arguments?.getString("pokemon")?.let { pokemon ->
            PokemonDetailsScreen(
                animatedVisibilityScope = this,
                pokemon = pokemon.getPokemonFromJson(),
            ) { navController.popBackStack() }
        }
    }
}
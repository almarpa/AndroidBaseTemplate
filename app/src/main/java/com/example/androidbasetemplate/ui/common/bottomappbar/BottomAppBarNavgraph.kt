package com.example.androidbasetemplate.ui.common.bottomappbar

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.androidbasetemplate.entity.Pokemon.Companion.getPokemonFromJson
import com.example.androidbasetemplate.ui.common.navigation.NavigationActions
import com.example.androidbasetemplate.ui.common.navigation.Routes
import com.example.androidbasetemplate.ui.favorites.FavouritesScreen
import com.example.androidbasetemplate.ui.pokemonlist.PokemonListScreen
import com.example.androidbasetemplate.ui.pokemonlist.details.PokemonDetailsScreen

context(SharedTransitionScope)
@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class
)
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
    composable(Routes.Favorites.route) {
        FavouritesScreen(
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
package com.example.androidbasetemplate.ui.common.bottomappbar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.androidbasetemplate.common.utils.getPokemonFromJson
import com.example.androidbasetemplate.ui.common.navigation.NavigationActions
import com.example.androidbasetemplate.ui.common.navigation.TemplateDestinations.FAVORITE_LIST_ROUTE
import com.example.androidbasetemplate.ui.common.navigation.TemplateDestinations.POKEMON_DETAIL_ROUTE
import com.example.androidbasetemplate.ui.common.navigation.TemplateDestinations.POKEMON_LIST_ROUTE
import com.example.androidbasetemplate.ui.favorites.FavoriteListScreen
import com.example.androidbasetemplate.ui.pokemonlist.PokemonListScreen
import com.example.androidbasetemplate.ui.pokemonlist.details.PokemonDetailsScreen

context(SharedTransitionScope)
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class
)
fun NavGraphBuilder.bottomAppBarNavGraph(
    drawerState: DrawerState,
    currentRoute: String,
    navigationActions: NavigationActions,
    navController: NavController,
) {
    composable(POKEMON_LIST_ROUTE) {
        PokemonListScreen(
            animatedContentScope = this,
            drawerState = drawerState,
            currentRoute = currentRoute,
            navigationActions = navigationActions,
        )
    }
    composable(FAVORITE_LIST_ROUTE) {
        FavoriteListScreen(
            drawerState = drawerState,
            currentRoute = currentRoute,
            navigationActions = navigationActions,
        )
    }
    composable(
        route = POKEMON_DETAIL_ROUTE,
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
                animatedContentScope = this,
                pokemon = pokemon.getPokemonFromJson(),
            ) { navController.popBackStack() }
        }
    }
}
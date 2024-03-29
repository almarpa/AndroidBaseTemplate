package com.example.androidbasetemplate.ui.common.bottomappbar

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.androidbasetemplate.common.utils.getViewModel
import com.example.androidbasetemplate.ui.common.NavigationActions
import com.example.androidbasetemplate.ui.common.TemplateDestinations
import com.example.androidbasetemplate.ui.favorites.FavoriteListScreen
import com.example.androidbasetemplate.ui.favorites.FavoriteListViewModel
import com.example.androidbasetemplate.ui.pokemondetail.PokemonDetailScreen
import com.example.androidbasetemplate.ui.pokemondetail.PokemonDetailViewModel
import com.example.androidbasetemplate.ui.pokemonlist.PokemonListScreen
import com.example.androidbasetemplate.ui.pokemonlist.PokemonListViewModel

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.bottomAppBarNavGraph(
    navController: NavHostController,
    drawerState: DrawerState,
    currentRoute: String,
    navigationActions: NavigationActions,
) {
    composable(TemplateDestinations.POKEMON_LIST_ROUTE) {
        PokemonListScreen(
            pokemonListViewModel = LocalContext.current.getViewModel<PokemonListViewModel>(),
            drawerState = drawerState,
            currentRoute = currentRoute,
            navigationActions = navigationActions,
        ) { selectedPokemonID ->
            navigationActions.navigateToDetailNavGraph(selectedPokemonID)
        }
    }
    composable(TemplateDestinations.FAVORITE_LIST_ROUTE) {
        FavoriteListScreen(
            favoriteListViewModel = LocalContext.current.getViewModel<FavoriteListViewModel>(),
            drawerState = drawerState,
            currentRoute = currentRoute,
            navigationActions = navigationActions,
        )
    }
    composable(
        route = TemplateDestinations.POKEMON_DETAIL_ROUTE,
        arguments = listOf(navArgument("pokemonID") { type = NavType.IntType }),
    ) { navBackStackEntry ->
        navBackStackEntry.arguments?.getInt("pokemonID")?.let { selectedPokemonID ->
            PokemonDetailScreen(
                pokemonListViewModel = LocalContext.current.getViewModel<PokemonDetailViewModel>(),
                navController = navController,
                drawerState = drawerState,
                selectedPokemonID = selectedPokemonID,
            )
        }
    }
}
package com.example.androidbasetemplate.ui.common.bottomappbar

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.androidbasetemplate.common.utils.getViewModel
import com.example.androidbasetemplate.ui.common.NavigationActions
import com.example.androidbasetemplate.ui.common.TemplateDestinations
import com.example.androidbasetemplate.ui.favorites.FavoriteListScreen
import com.example.androidbasetemplate.ui.favorites.FavoriteListViewModel
import com.example.androidbasetemplate.ui.pokemonlist.PokemonListScreen
import com.example.androidbasetemplate.ui.pokemonlist.PokemonListViewModel

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.bottomAppBarNavGraph(
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
        )
    }
    composable(TemplateDestinations.FAVORITE_LIST_ROUTE) {
        FavoriteListScreen(
            favoriteListViewModel = LocalContext.current.getViewModel<FavoriteListViewModel>(),
            drawerState = drawerState,
            currentRoute = currentRoute,
            navigationActions = navigationActions,
        )
    }
}
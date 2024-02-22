package com.example.androidbasetemplate.ui.common.bottomappbar

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.androidbasetemplate.MainActivity
import com.example.androidbasetemplate.ui.common.NavigationActions
import com.example.androidbasetemplate.ui.common.TemplateDestinations
import com.example.androidbasetemplate.ui.common.navigateToDetailNavGraph
import com.example.androidbasetemplate.ui.favorites.FavoriteListScreen
import com.example.androidbasetemplate.ui.favorites.FavoriteListViewModel
import com.example.androidbasetemplate.ui.pokemonlist.PokemonListScreen
import com.example.androidbasetemplate.ui.pokemonlist.PokemonListViewModel
import com.example.androidbasetemplate.ui.splash.SplashScreen

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.bottomAppBarNavGraph(
    navController: NavHostController,
    drawerState: DrawerState,
    currentRoute: String,
    navigationActions: NavigationActions,
) {
    composable(TemplateDestinations.SPLASH_ROUTE) {
        SplashScreen(navigationActions = navigationActions)
    }
    composable(TemplateDestinations.POKEMON_LIST_ROUTE) {
        PokemonListScreen(
            pokemonListViewModel = ViewModelProvider(LocalContext.current as MainActivity)[PokemonListViewModel::class.java],
            drawerState = drawerState,
            currentRoute = currentRoute,
            navigationActions = navigationActions,
        ) { selectedPokemonID ->
            navController.navigateToDetailNavGraph(selectedPokemonID)
        }
    }
    composable(TemplateDestinations.FAVORITE_LIST_ROUTE) {
        FavoriteListScreen(
            favoriteListViewModel = ViewModelProvider(LocalContext.current as MainActivity)[FavoriteListViewModel::class.java],
            drawerState = drawerState,
            currentRoute = currentRoute,
            navigationActions = navigationActions,
        )
    }
}

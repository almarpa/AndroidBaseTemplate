package com.example.androidbasetemplate.ui.common

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.androidbasetemplate.MainActivity
import com.example.androidbasetemplate.ui.TemplateDestinations
import com.example.androidbasetemplate.ui.TemplateNavigationActions
import com.example.androidbasetemplate.ui.favorites.FavoriteListScreen
import com.example.androidbasetemplate.ui.favorites.FavoriteListViewModel
import com.example.androidbasetemplate.ui.home.HomeScreen
import com.example.androidbasetemplate.ui.home.HomeViewModel
import com.example.androidbasetemplate.ui.navigateToDetailNavGraph
import com.example.androidbasetemplate.ui.pokemonlist.PokemonListScreen
import com.example.androidbasetemplate.ui.pokemonlist.PokemonListViewModel

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.bottomAppBarNavGraph(
    navController: NavHostController,
    drawerState: DrawerState,
    navigationActions: TemplateNavigationActions,
) {
    composable(TemplateDestinations.HOME_ROUTE) {
        HomeScreen(
            homeViewModel = ViewModelProvider(LocalContext.current as MainActivity)[HomeViewModel::class.java],
            drawerState = drawerState,
            navigationActions = navigationActions
        )
    }
    composable(TemplateDestinations.POKEMON_LIST_ROUTE) {
        PokemonListScreen(
            pokemonListViewModel = ViewModelProvider(LocalContext.current as MainActivity)[PokemonListViewModel::class.java],
            drawerState = drawerState,
            navigationActions = navigationActions,
        ) { selectedPokemonID ->
            navController.navigateToDetailNavGraph(selectedPokemonID)
        }
    }
    composable(TemplateDestinations.FAVORITE_LIST_ROUTE) {
        FavoriteListScreen(
            favoriteListViewModel = ViewModelProvider(LocalContext.current as MainActivity)[FavoriteListViewModel::class.java],
            drawerState = drawerState,
            navigationActions = navigationActions,
        )
    }
}

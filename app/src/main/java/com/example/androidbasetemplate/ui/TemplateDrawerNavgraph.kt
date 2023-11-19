package com.example.androidbasetemplate.ui

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.androidbasetemplate.MainActivity
import com.example.androidbasetemplate.ui.home.HomeScreen
import com.example.androidbasetemplate.ui.home.HomeViewModel
import com.example.androidbasetemplate.ui.pokemonlist.PokemonListScreen
import com.example.androidbasetemplate.ui.pokemonlist.PokemonListViewModel

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.drawerNavGraph(
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
    composable(TemplateDestinations.POKEMON_LIST) {
        PokemonListScreen(
            pokemonListViewModel = ViewModelProvider(LocalContext.current as MainActivity)[PokemonListViewModel::class.java],
            drawerState = drawerState,
            navigationActions = navigationActions,
        ) { selectedPokemonID ->
            navController.navigateToDetailNavGraph(selectedPokemonID)
        }
    }
}

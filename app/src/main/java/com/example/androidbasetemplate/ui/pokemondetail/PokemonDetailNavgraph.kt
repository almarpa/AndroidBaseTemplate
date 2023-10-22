package com.example.androidbasetemplate.ui.pokemondetail

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.androidbasetemplate.MainActivity
import com.example.androidbasetemplate.ui.TemplateDestinations
import com.example.androidbasetemplate.ui.TemplateNavigationActions

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.pokemonDetailNavGraph(
    drawerState: DrawerState,
    navigationActions: TemplateNavigationActions,
) {
    composable(
        route = TemplateDestinations.POKEMON_DETAIL,
        arguments = listOf(navArgument("pokemonID") { type = NavType.IntType }),
    ) { navBackStackEntry ->
        navBackStackEntry.arguments?.getInt("pokemonID")?.let { selectedPokemonID ->
            PokemonDetailScreen(
                pokemonListViewModel = ViewModelProvider(LocalContext.current as MainActivity)[PokemonDetailViewModel::class.java],
                drawerState = drawerState,
                navigationActions = navigationActions,
                selectedPokemonID = selectedPokemonID,
            )
        }
    }
}
